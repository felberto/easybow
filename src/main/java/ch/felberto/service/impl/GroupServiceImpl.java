package ch.felberto.service.impl;

import ch.felberto.domain.Group;
import ch.felberto.repository.GroupRepository;
import ch.felberto.service.GroupService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Group}.
 */
@Service
@Transactional
public class GroupServiceImpl implements GroupService {

    private final Logger log = LoggerFactory.getLogger(GroupServiceImpl.class);

    private final GroupRepository groupRepository;

    public GroupServiceImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public Group save(Group group) {
        log.debug("Request to save Group : {}", group);
        return groupRepository.save(group);
    }

    @Override
    public Optional<Group> partialUpdate(Group group) {
        log.debug("Request to partially update Group : {}", group);

        return groupRepository
            .findById(group.getId())
            .map(
                existingGroup -> {
                    if (group.getName() != null) {
                        existingGroup.setName(group.getName());
                    }

                    return existingGroup;
                }
            )
            .map(groupRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Group> findAll(Pageable pageable) {
        log.debug("Request to get all Groups");
        return groupRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Group> findOne(Long id) {
        log.debug("Request to get Group : {}", id);
        return groupRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Group : {}", id);
        groupRepository.deleteById(id);
    }

    @Override
    public List<Group> findByCompetition(Long id) {
        log.debug("Request to get Group by Competition : {}", id);
        return groupRepository.findByCompetition_Id(id);
    }

    @Override
    public List<Group> findByCompetitionAndClub(Long competitionId, String club) {
        log.debug("Request to get Group by Competition : {} and Club : {}", competitionId, club);
        List<Group> groupList = groupRepository.findByCompetition_Id(competitionId);
        String originalName = "";
        switch (club) {
            case "ROTHENBURG":
                originalName = "ASV Rothenburg";
                break;
            case "EMMENBRUECKE":
                originalName = "ASG Emmenbrücke";
                break;
            case "STEINHAUSEN":
                originalName = "ASG Steinhausen";
                break;
            case "ETTISWIL":
                originalName = "ASG Brestenegg-Ettiswil";
                break;
            case "AEGERITAL":
                originalName = "ASV Ägerital";
                break;
            case "GURTNELLEN":
                originalName = "ASG Gurtnellen";
                break;
            case "DALLENWIL":
                originalName = "ASV Dallenwil";
                break;
            case "REINACH":
                originalName = "ASV Reinach-Birseck";
                break;
            case "MERLISCHACHEN":
                originalName = "ASV Merlischachen";
                break;
            default:
                originalName = "";
                break;
        }
        String finalOriginalName = originalName;
        return groupList.stream().filter(r -> r.getClub().getName().equals(finalOriginalName)).collect(Collectors.toList());
    }

    @Override
    public List<Group> findByCompetitionAndClubOnlyOne(Long competitionId, Long clubId) {
        log.debug("Request to get Group by Competition : {} and Club : {}", competitionId, clubId);
        return groupRepository.findByCompetition_IdAndClub_IdAndRound(competitionId, clubId, 1);
    }
}
