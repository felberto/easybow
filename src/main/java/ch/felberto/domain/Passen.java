package ch.felberto.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Passen.
 */
@Entity
@Table(name = "passen")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Passen implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Min(value = 0)
    @Max(value = 11)
    @Column(name = "p_1")
    private Integer p1;

    @Min(value = 0)
    @Max(value = 11)
    @Column(name = "p_2")
    private Integer p2;

    @Min(value = 0)
    @Max(value = 11)
    @Column(name = "p_3")
    private Integer p3;

    @Min(value = 0)
    @Max(value = 11)
    @Column(name = "p_4")
    private Integer p4;

    @Min(value = 0)
    @Max(value = 11)
    @Column(name = "p_5")
    private Integer p5;

    @Min(value = 0)
    @Max(value = 11)
    @Column(name = "p_6")
    private Integer p6;

    @Min(value = 0)
    @Max(value = 11)
    @Column(name = "p_7")
    private Integer p7;

    @Min(value = 0)
    @Max(value = 11)
    @Column(name = "p_8")
    private Integer p8;

    @Min(value = 0)
    @Max(value = 11)
    @Column(name = "p_9")
    private Integer p9;

    @Min(value = 0)
    @Max(value = 11)
    @Column(name = "p_10")
    private Integer p10;

    @Column(name = "resultat")
    private Integer resultat;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Passen id(Long id) {
        this.id = id;
        return this;
    }

    public Integer getp1() {
        return this.p1;
    }

    public Passen p1(Integer p1) {
        this.p1 = p1;
        return this;
    }

    public void setp1(Integer p1) {
        this.p1 = p1;
    }

    public Integer getp2() {
        return this.p2;
    }

    public Passen p2(Integer p2) {
        this.p2 = p2;
        return this;
    }

    public void setp2(Integer p2) {
        this.p2 = p2;
    }

    public Integer getp3() {
        return this.p3;
    }

    public Passen p3(Integer p3) {
        this.p3 = p3;
        return this;
    }

    public void setp3(Integer p3) {
        this.p3 = p3;
    }

    public Integer getp4() {
        return this.p4;
    }

    public Passen p4(Integer p4) {
        this.p4 = p4;
        return this;
    }

    public void setp4(Integer p4) {
        this.p4 = p4;
    }

    public Integer getp5() {
        return this.p5;
    }

    public Passen p5(Integer p5) {
        this.p5 = p5;
        return this;
    }

    public void setp5(Integer p5) {
        this.p5 = p5;
    }

    public Integer getp6() {
        return this.p6;
    }

    public Passen p6(Integer p6) {
        this.p6 = p6;
        return this;
    }

    public void setp6(Integer p6) {
        this.p6 = p6;
    }

    public Integer getp7() {
        return this.p7;
    }

    public Passen p7(Integer p7) {
        this.p7 = p7;
        return this;
    }

    public void setp7(Integer p7) {
        this.p7 = p7;
    }

    public Integer getp8() {
        return this.p8;
    }

    public Passen p8(Integer p8) {
        this.p8 = p8;
        return this;
    }

    public void setp8(Integer p8) {
        this.p8 = p8;
    }

    public Integer getp9() {
        return this.p9;
    }

    public Passen p9(Integer p9) {
        this.p9 = p9;
        return this;
    }

    public void setp9(Integer p9) {
        this.p9 = p9;
    }

    public Integer getp10() {
        return this.p10;
    }

    public Passen p10(Integer p10) {
        this.p10 = p10;
        return this;
    }

    public void setp10(Integer p10) {
        this.p10 = p10;
    }

    public Integer getResultat() {
        return this.resultat;
    }

    public Passen resultat(Integer resultat) {
        this.resultat = resultat;
        return this;
    }

    public void setResultat(Integer resultat) {
        this.resultat = resultat;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Passen)) {
            return false;
        }
        return id != null && id.equals(((Passen) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Passen{" +
            "id=" + getId() +
            ", p1=" + getp1() +
            ", p2=" + getp2() +
            ", p3=" + getp3() +
            ", p4=" + getp4() +
            ", p5=" + getp5() +
            ", p6=" + getp6() +
            ", p7=" + getp7() +
            ", p8=" + getp8() +
            ", p9=" + getp9() +
            ", p10=" + getp10() +
            ", resultat=" + getResultat() +
            "}";
    }
}
