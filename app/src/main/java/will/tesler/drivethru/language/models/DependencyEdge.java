package will.tesler.drivethru.language.models;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

public class DependencyEdge {

    public int headTokenIndex;
    public @Label String label;

    @Retention(SOURCE)
    @StringDef({
            UNKNOWN,
            ABBREV,
            ADVCL,
            ADVMOD,
            AMOD,
            APPOS,
            ATTR,
            AUX,
            AUXPASS,
            CC,
            CCOMP,
            CONJ,
            CSUBJ,
            CSUBJPASS,
            DEP,
            DET,
            DISCOURSE,
            DOBJ,
            EXPL,
            GOESWITH,
            IOBJ,
            MARK,
            MWE,
            MWV,
            NEG,
            NN,
            NPADVMOD,
            NSUBJ,
            NSUBJPASS,
            NUM,
            NUMBER,
            P,
            PARATAXIS,
            PARTMOD,
            PCOMP,
            POBJ,
            POSS,
            POSTNEG,
            PRECOMP,
            PRECONJ,
            PREDET,
            PREF,
            PREP,
            PRONL,
            PRT,
            PS,
            QUANTMOD,
            RCMOD,
            RCMODREL,
            RDROP,
            REF,
            REMNANT,
            REPARANDUM,
            ROOT,
            SNUM,
            SUFF,
            TMOD,
            TOPIC,
            VMOD,
            VOCATIVE,
            XCOMP,
            SUFFIX,
            TITLE,
            ADVPHMOD,
            AUXCAUS,
            AUXVV,
            DTMOD,
            FOREIGN,
            KW,
            LIST,
            NOMC,
            NOMCSUBJ,
            NOMCSUBJPASS,
            NUMC,
            COP,
            DISLOCATED,
    })
    public @interface Label {}
    public static final String UNKNOWN = "UNKNOWN";
    public static final String ABBREV = "ABBREV";
    public static final String ADVCL = "ACOMP";
    public static final String ADVMOD = "ADVMOD";
    public static final String AMOD = "AMOD";
    public static final String APPOS = "APPOS";
    public static final String ATTR = "ATTR";
    public static final String AUX = "AUX";
    public static final String AUXPASS = "AUXPASS";
    public static final String CC = "CC";
    public static final String CCOMP = "CCOMP";
    public static final String CONJ = "CONJ";
    public static final String CSUBJ = "CSUBJ";
    public static final String CSUBJPASS = "CSUBJPASS";
    public static final String DEP = "DEP";
    public static final String DET = "DET";
    public static final String DISCOURSE = "DISCOURSE";
    public static final String DOBJ = "DOBJ";
    public static final String EXPL = "EXPL";
    public static final String GOESWITH = "GOESWITH";
    public static final String IOBJ = "IOBJ";
    public static final String MARK = "MARK";
    public static final String MWE = "MWE";
    public static final String MWV = "MWV";
    public static final String NEG = "NEG";
    public static final String NN = "NN";
    public static final String NPADVMOD = "NPADVMOD";
    public static final String NSUBJ = "NSUBJ";
    public static final String NSUBJPASS = "NSUBJPASS";
    public static final String NUM = "NUM";
    public static final String NUMBER = "NUMBER";
    public static final String P = "P";
    public static final String PARATAXIS = "PARATAXIS";
    public static final String PARTMOD = "PARTMOD";
    public static final String PCOMP = "PCOMP";
    public static final String POBJ = "POBJ";
    public static final String POSS = "POSS";
    public static final String POSTNEG = "POSTNEG";
    public static final String PRECOMP = "PRECOMP";
    public static final String PRECONJ = "PRECONJ";
    public static final String PREDET = "PREDET";
    public static final String PREF = "PREF";
    public static final String PREP = "PREP";
    public static final String PRONL = "PRONL";
    public static final String PRT = "PRT";
    public static final String PS = "PS";
    public static final String QUANTMOD = "QUANTMOD";
    public static final String RCMOD = "RCMOD";
    public static final String RCMODREL = "RCMODREL";
    public static final String RDROP = "RDROP";
    public static final String REF = "REF";
    public static final String REMNANT = "REMNANT";
    public static final String REPARANDUM = "REPARANDUM";
    public static final String ROOT = "ROOT";
    public static final String SNUM = "SNUM";
    public static final String SUFF = "SUFF";
    public static final String TMOD = "TMOD";
    public static final String TOPIC = "TOPIC";
    public static final String VMOD = "VMOD";
    public static final String VOCATIVE = "VOCATIVE";
    public static final String XCOMP = "XCOMP";
    public static final String SUFFIX = "SUFFIX";
    public static final String TITLE = "TITLE";
    public static final String ADVPHMOD = "ADVPHMOD";
    public static final String AUXCAUS = "AUXCAUS";
    public static final String AUXVV = "AUXVV";
    public static final String DTMOD = "DTMOD";
    public static final String FOREIGN = "FOREIGN";
    public static final String KW = "KW";
    public static final String LIST = "LIST";
    public static final String NOMC = "NOMC";
    public static final String NOMCSUBJ = "NOMCSUBJ";
    public static final String NOMCSUBJPASS = "NOMCSUBJPASS";
    public static final String NUMC = "NUMC";
    public static final String COP = "COP";
    public static final String DISLOCATED = "DISLOCATED";
}
