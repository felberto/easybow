package ch.felberto.service.impl;

import ch.felberto.domain.Series;
import ch.felberto.repository.SeriesRepository;
import ch.felberto.service.SeriesService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Series}.
 */
@Service
@Transactional
public class SeriesServiceImpl implements SeriesService {

    private final Logger log = LoggerFactory.getLogger(SeriesServiceImpl.class);

    private final SeriesRepository seriesRepository;

    public SeriesServiceImpl(SeriesRepository seriesRepository) {
        this.seriesRepository = seriesRepository;
    }

    @Override
    public Series save(Series series) {
        log.debug("Request to save Serie : {}", series);
        return seriesRepository.save(series);
    }

    @Override
    public Optional<Series> partialUpdate(Series series) {
        log.debug("Request to partially update Serie : {}", series);

        return seriesRepository
            .findById(series.getId())
            .map(
                existingSerie -> {
                    if (series.getp1() != null) {
                        existingSerie.setp1(series.getp1());
                    }
                    if (series.getp2() != null) {
                        existingSerie.setp2(series.getp2());
                    }
                    if (series.getp3() != null) {
                        existingSerie.setp3(series.getp3());
                    }
                    if (series.getp4() != null) {
                        existingSerie.setp4(series.getp4());
                    }
                    if (series.getp5() != null) {
                        existingSerie.setp5(series.getp5());
                    }
                    if (series.getp6() != null) {
                        existingSerie.setp6(series.getp6());
                    }
                    if (series.getp7() != null) {
                        existingSerie.setp7(series.getp7());
                    }
                    if (series.getp8() != null) {
                        existingSerie.setp8(series.getp8());
                    }
                    if (series.getp9() != null) {
                        existingSerie.setp9(series.getp9());
                    }
                    if (series.getp10() != null) {
                        existingSerie.setp10(series.getp10());
                    }
                    if (series.getResult() != null) {
                        existingSerie.setResult(series.getResult());
                    }

                    return existingSerie;
                }
            )
            .map(seriesRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Series> findAll(Pageable pageable) {
        log.debug("Request to get all Series");
        return seriesRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Series> findOne(Long id) {
        log.debug("Request to get Serie : {}", id);
        return seriesRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Serie : {}", id);
        seriesRepository.deleteById(id);
    }
}
