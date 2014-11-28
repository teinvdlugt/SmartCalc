package com.teinproductions.tein.integerfactorization;


import android.content.Context;

public enum Element {

    HYDROGEN(R.string.hydrogen,"H",1.0079,1,1766),
    HELIUM(R.string.helium,"He",4.0026,2,1895),
    LITHIUM(R.string.lithium,"Li",6.941,3,1817),
    BERYLLIUM(R.string.beryllium,"Be",9.0122,4,1797),
    BORON(R.string.boron,"B",10.811,5,1808),
    CARBON(R.string.carbon,"C",12.0107,6,0),
    NITROGEN(R.string.nitrogen,"N",14.0067,7,1772),
    OXYGEN(R.string.oxygen,"O",15.9994,8,1774),
    FLUORINE(R.string.fluorine,"F",18.9984,9,1886),
    NEON(R.string.neon,"Ne",20.1797,10,1898),
    SODIUM(R.string.sodium,"Na",22.9897,11,1807),
    MAGNESIUM(R.string.magnesium,"Mg",24.305,12,1755),
    ALUMINUM(R.string.aluminum,"Al",26.9815,13,1825),
    SILICON(R.string.silicon, "Si",28.0855,14,1824),
    PHOSPHORUS(R.string.phosphorus,"P",30.9738,15,1669),
    SULFUR(R.string.sulfur,"S",32.065,16,-500),
    CHLORINE(R.string.chlorine,"Cl",35.453,17,1774),
    ARGON(R.string.argon,"Ar",39.948,18,1894),
    POTASSIUM(R.string.potassium,"K",39.0983,19,1807),
    CALCIUM(R.string.calcium,"Ca",40.078,20,1808),
    SCANDIUM(R.string.scandium,"Sc",44.9559,21,1879),
    TITANIUM(R.string.titanium,"Ti",47.867,22,1791),
    VANADIUM(R.string.vanadium,"V",50.9415,23,1801),
    CHROMIUM(R.string.chromium,"Cr",51.9961,24,1797),
    MANGANESE(R.string.manganese,"Mn",54.938,25,1774),
    IRON(R.string.iron,"Fe",55.845,26,-2000),
    COBALT(R.string.cobalt,"Co",58.6934,27,1735),
    NICKEL(R.string.nickel,"Ni",58.6934,28,1751),
    COPPER(R.string.copper,"Cu",63.546,29,-8000),
    ZINC(R.string.zinc,"Zn",65.39,30,1500),
    GALLIUM(R.string.gallium,"Ga",69.723,31,1875),
    GERMANIUM(R.string.germanium,"Ge",72.64,32,1886),
    ARSENIC(R.string.arsenic,"As",74.9216,33,1250),
    SELENIUM(R.string.selenium,"Se",78.96,34,1817),
    BROMINE(R.string.bromine,"Br",79.904,35,1826),
    KRYPTON(R.string.krypton,"Kr",83.8,36,1898),
    RUBIDIUM(R.string.rubidium,"Rb",85.4678,37,1861),
    STRONTIUM(R.string.strontium,"Sr",87.62,38,1790),
    YTTRIUM(R.string.yttrium,"Y",88.9095,39,1794),
    ZIRCONIUM(R.string.zirconium,"Zr",91.224,40,1789),
    NIOBIUM(R.string.niobium,"Nb",92.9064,41,1801),
    MOLYBDENUM(R.string.molybdenum,"Mo",95.94,42,1781),
    TECHNETIUM(R.string.technetium,"Tc",98.0,43,1937),
    RUTHENIUM(R.string.ruthenium,"Ru",101.07,44,1844),
    RHODIUM(R.string.rhodium,"Rh",102.9055,45,1803),
    PALLADIUM(R.string.palladium,"Pd",106.42,46,1803),
    SILVER(R.string.silver,"Ag",107.8682,47,-3000),
    CADMIUM(R.string.cadmium,"Cd",112.411,48,1817),
    INDIUM(R.string.indium,"In",114.818,49,1863),
    TIN(R.string.tin,"Sn",118.71,50,-3000),
    ANTIMONY(R.string.antimony,"Sb",121.76,51,-3000),
    TELLURIUM(R.string.tellurium,"Te",127.6,52,1783),
    IODINE(R.string.iodine,"I",126.9045,53,1811),
    XENON(R.string.xenon,"Xe",131.293,54,1898),
    CESIUM(R.string.cesium,"Cs",132.9055,55,1860),
    BARIUM(R.string.barium,"Ba",137.327,56,1808),
    LANTHANUM(R.string.lanthanum,"La",138.9055,57,1839),
    CERIUM(R.string.cerium,"Ce",140.116,58,1803),
    PRASEODYMIUM(R.string.praseodymium,"Pr",140.9077,59,1885),
    NEODYMIUM(R.string.neodymium,"Nd",144.24,60,1885),
    PROMETHIUM(R.string.promethium,"Pm",(double)145,61,1945),
    SAMARIUM(R.string.samarium,"Sm",150.36,62,1879),
    EUROPIUM(R.string.europium,"Eu",151.964,63,1901),
    GADOLINIUM(R.string.gadolinium,"Gd",157.25,64,1880),
    TERBIUM(R.string.terbium,"Tb",158.9253,65,1834),
    DYSPROSIUM(R.string.dysprosium,"Dy",162.5,66,1886),
    HOLMIUM(R.string.holmium,"Ho",164.9303,67,1878),
    ERBIUM(R.string.erbium,"Er",167.259,68,1842),
    THULIUM(R.string.thulium,"Tm",168.9342,69,1879),
    YTTERBIUM(R.string.ytterbium,"Yb",173.04,70,1878),
    LUTETIUM(R.string.lutetium,"Lu",174.967,71,1907),
    HAFNIUM(R.string.hafnium,"Hf",178.49,72,1923),
    TANTALUM(R.string.tantalum,"Ta",180.9479,73,1802),
    TUNGSTEN(R.string.tungsten,"W",183.84,74,1783),
    RHENIUM(R.string.rhenium,"Re",186.207,75,1925),
    OSMIUM(R.string.osmium,"Os",190.23,76,1803),
    IRIDIUM(R.string.iridium,"Ir",192.217,77,1803),
    PLATINUM(R.string.platinum,"Pt",195.078,78,1735),
    GOLD(R.string.gold,"Au",196.9665,79,-2500),
    MERCURY(R.string.mercury,"Hg",200.59,80,-1500),
    THALLIUM(R.string.thallium,"Tl",204.3833,81,1861),
    LEAD(R.string.lead,"Pb",207.2,82,-4000),
    BISMUTH(R.string.bismuth,"Bi",208.9804,83,1400),
    POLONIUM(R.string.polonium,"Po", 209.0, 84,1898),
    ASTATINE(R.string.astatine,"At", 210.0, 85,1940),
    RADON(R.string.radon,"Rn",222.0,86,1900),
    FRANCIUM(R.string.francium,"Fr",223.0,87,1939),
    RADIUM(R.string.radium,"Ra",226.0,88,1898),
    ACTINIUM(R.string.actinium,"Ac",227.0,89,1899),
    THORIUM(R.string.thorium,"Th",232.0381,90,1829),
    PROTACTINIUM(R.string.protactinium,"Pa",231.0359,91,1913),
    URANIUM(R.string.uranium,"U",238.0289,92,1789),
    NEPTUNIUM(R.string.neptunium,"Np",237.0,93,1940),
    PLUTONIUM(R.string.plutonium,"Pu",244.0,94,1940),
    AMERICIUM(R.string.americium,"Am",243.0,95,1944),
    CURIUM(R.string.curium,"Cm",247.0,96,1944),
    BERKELIUM(R.string.berkelium,"Bk",247.0,97,1949),
    CALIFORNIUM(R.string.californium,"Cf",251.0,98,1950),
    EINSTEINIUM(R.string.einsteinium,"Es",252.0,99,1952),
    FERMIUM(R.string.fermium,"Fm",257.0,100,1952),
    MENDELEVIUM(R.string.mendelevium,"Md",258.0,101,1955),
    NOBELIUM(R.string.nobelium,"No",259.0,102,1958),
    LAWRENCIUM(R.string.lawrencium,"Lr",262.0,103,1961),
    RUTHERFORDIUM(R.string.rutherfordium,"Rf",261.0,104,1964),
    DUBNIUM(R.string.dubnium,"Db",262.0,105,1967),
    SEABORGIUM(R.string.seaborgium,"Sg",266.0,106,1974),
    BOHRIUM(R.string.bohrium,"Bh",264.0,107,1981),
    HASSIUM(R.string.hassium,"Hs",277.0,108,1984),
    MEITNERIUM(R.string.meitnerium,"Mt",269.0,109,1982),
    DARMSTADTIUM(R.string.darmstadtium,"Ds",281.0,110,1994),
    ROENTGENIUM(R.string.roentgenium,"Rg",281.0,111,1994),
    COPERNICIUM(R.string.copernicium,"Cn",285.0,112,1996),
    UNUNTRIUM(R.string.ununtrium,"Uut",286.0,113,2004),
    FLEROVIUM(R.string.flerovium,"Fl",289.0,114,1998),
    UNUNPENTIUM(R.string.ununpentium,"Uup",289.0,115,2004),
    LIVERMORIUM(R.string.livermorium,"Lv",293.0,116,200),
    UNUNSEPTIUM(R.string.ununseptium,"Uus",294.0,117,0),
    UNUNOCTIUM(R.string.ununoctium,"Uuo",294.0,118,2006);

    private int name;
    private String abbreviation;
    private int atomicNumber;
    private int discYear;
    private Double mass;

    public static final Double nA = 602214000000000000000000.0;

    Element(int name, String abbreviation, Double mass, Integer atomicNumber, Integer yearOfDiscovery) {
        this.name = name;
        this.abbreviation = abbreviation;
        this.mass = mass;
        this.atomicNumber = atomicNumber;
        this.discYear = yearOfDiscovery;
    }


    public Double calculateGramWhenMolGiven(Double givenMol){
        return givenMol * this.mass;
    }

    public Double calculateParticlesWhenMolGiven(Double givenMol){
        return givenMol * nA;
    }

    public Double calculateMolWhenGramGiven(Double givenGram){
        return givenGram / this.mass;
    }

    public Double calculateMolWhenParticlesGiven(Integer givenParticles){
        return givenParticles / nA;
    }


    public static Element findElementByAbbreviation(String abbreviation){
        for(Element currentElement : Element.values()){
            if(abbreviation.equalsIgnoreCase(currentElement.getAbbreviation())){
                return currentElement;
            }
        }

        return null;
    }

    public static Element findElementByName(Context context, String name){
        for(Element currentElement : Element.values()){
            if(currentElement.getName(context).equalsIgnoreCase(name)) {
                return currentElement;
            }
        }

        return null;
    }

    public static Element findElementByAbbreviationOrName(Context context, String nameOrAbbreviation){
        Element foundElement;
        foundElement = Element.findElementByAbbreviation(nameOrAbbreviation);
        if(foundElement == null){
            foundElement = Element.findElementByName(context, nameOrAbbreviation);
        }

        return foundElement;
    }


    public ElementFragment toFragment(){
        return ElementFragment.newInstance(this);
    }


    public String getName(Context context) {
        return context.getString(name);
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public Double getMass() {
        return mass;
    }

    public Integer getAtomicNumber() {
        return atomicNumber;
    }

    public Integer getDiscYear() {
        return discYear;
    }

    public String getDiscYearString(Context context) {
        if (this.discYear == 0){
            return context.getString(R.string.unknown);
        } else if (this.discYear < 0) {
            return Integer.toString(0 - this.discYear) + " " + context.getString(R.string.before_christ);
        } else {
            return Integer.toString(this.discYear);
        }
    }

}

