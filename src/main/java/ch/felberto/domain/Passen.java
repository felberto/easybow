package ch.felberto.domain;

import java.io.Serializable;
import javax.persistence.*;
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

    @Column(name = "p_1")
    private Long p1;

    @Column(name = "p_2")
    private Long p2;

    @Column(name = "p_3")
    private Long p3;

    @Column(name = "p_4")
    private Long p4;

    @Column(name = "p_5")
    private Long p5;

    @Column(name = "p_6")
    private Long p6;

    @Column(name = "p_7")
    private Long p7;

    @Column(name = "p_8")
    private Long p8;

    @Column(name = "p_9")
    private Long p9;

    @Column(name = "p_10")
    private Long p10;

    @Column(name = "resultat")
    private Long resultat;

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

    public Long getp1() {
        return this.p1;
    }

    public Passen p1(Long p1) {
        this.p1 = p1;
        return this;
    }

    public void setp1(Long p1) {
        this.p1 = p1;
    }

    public Long getp2() {
        return this.p2;
    }

    public Passen p2(Long p2) {
        this.p2 = p2;
        return this;
    }

    public void setp2(Long p2) {
        this.p2 = p2;
    }

    public Long getp3() {
        return this.p3;
    }

    public Passen p3(Long p3) {
        this.p3 = p3;
        return this;
    }

    public void setp3(Long p3) {
        this.p3 = p3;
    }

    public Long getp4() {
        return this.p4;
    }

    public Passen p4(Long p4) {
        this.p4 = p4;
        return this;
    }

    public void setp4(Long p4) {
        this.p4 = p4;
    }

    public Long getp5() {
        return this.p5;
    }

    public Passen p5(Long p5) {
        this.p5 = p5;
        return this;
    }

    public void setp5(Long p5) {
        this.p5 = p5;
    }

    public Long getp6() {
        return this.p6;
    }

    public Passen p6(Long p6) {
        this.p6 = p6;
        return this;
    }

    public void setp6(Long p6) {
        this.p6 = p6;
    }

    public Long getp7() {
        return this.p7;
    }

    public Passen p7(Long p7) {
        this.p7 = p7;
        return this;
    }

    public void setp7(Long p7) {
        this.p7 = p7;
    }

    public Long getp8() {
        return this.p8;
    }

    public Passen p8(Long p8) {
        this.p8 = p8;
        return this;
    }

    public void setp8(Long p8) {
        this.p8 = p8;
    }

    public Long getp9() {
        return this.p9;
    }

    public Passen p9(Long p9) {
        this.p9 = p9;
        return this;
    }

    public void setp9(Long p9) {
        this.p9 = p9;
    }

    public Long getp10() {
        return this.p10;
    }

    public Passen p10(Long p10) {
        this.p10 = p10;
        return this;
    }

    public void setp10(Long p10) {
        this.p10 = p10;
    }

    public Long getResultat() {
        return this.resultat;
    }

    public Passen resultat(Long resultat) {
        this.resultat = resultat;
        return this;
    }

    public void setResultat(Long resultat) {
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
