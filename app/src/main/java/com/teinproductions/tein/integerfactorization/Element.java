package com.teinproductions.tein.integerfactorization;


public enum Element {

    HYDROGEN(MainActivity.context.getResources().getString(R.string.hydrogen),"H",1.0079,1,1766,null),
    HELIUM(MainActivity.context.getResources().getString(R.string.helium),"He",4.0026,2,1895,null),
    LITHIUM(MainActivity.context.getResources().getString(R.string.lithium),"Li",6.941,3,1817,R.color.silver),
    BERYLLIUM(MainActivity.context.getResources().getString(R.string.beryllium),"Be",9.0122,4,1797,R.color.slate_gray),
    BORON(MainActivity.context.getResources().getString(R.string.boron),"B",10.811,5,1808,null),
    CARBON(MainActivity.context.getResources().getString(R.string.carbon),"C",12.0107,6,0,null),
    NITROGEN(MainActivity.context.getResources().getString(R.string.nitrogen),"N",14.0067,7,1772,null),
    OXYGEN(MainActivity.context.getResources().getString(R.string.oxygen),"O",15.9994,8,1774,null),
    FLUORINE(MainActivity.context.getResources().getString(R.string.fluorine),"F",18.9984,9,1886,null),
    NEON(MainActivity.context.getResources().getString(R.string.neon),"Ne",20.1797,10,1898,null),
    SODIUM(MainActivity.context.getResources().getString(R.string.sodium),"Na",22.9897,11,1807,R.color.silver),
    MAGNESIUM(MainActivity.context.getResources().getString(R.string.magnesium),"Mg",24.305,12,1755,R.color.silver),
    ALUMINUM(MainActivity.context.getResources().getString(R.string.aluminum),"Al",26.9815,13,1825,R.color.silver),
    SILICON(MainActivity.context.getResources().getString(R.string.silicon),"Si",28.0855,14,1824,R.color.gray),
    PHOSPHORUS(MainActivity.context.getResources().getString(R.string.phosphorus),"P",30.9738,15,1669,null),
    SULFUR(MainActivity.context.getResources().getString(R.string.sulfur),"S",32.065,16,-500,R.color.yellow),
    CHLORINE(MainActivity.context.getResources().getString(R.string.chlorine),"Cl",35.453,17,1774,R.color.yellow),
    ARGON(MainActivity.context.getResources().getString(R.string.argon),"Ar",39.948,18,1894,null),
    POTASSIUM(MainActivity.context.getResources().getString(R.string.potassium),"K",39.0983,19,1807,R.color.silver),
    CALCIUM(MainActivity.context.getResources().getString(R.string.calcium),"Ca",40.078,20,1808,R.color.silver),
    SCANDIUM(MainActivity.context.getResources().getString(R.string.scandium),"Sc",44.9559,21,1879,R.color.silver),
    TITANIUM(MainActivity.context.getResources().getString(R.string.titanium),"Ti",47.867,22,1791,R.color.silver),
    VANADIUM(MainActivity.context.getResources().getString(R.string.vanadium),"V",50.9415,23,1801,R.color.silver),
    CHROMIUM(MainActivity.context.getResources().getString(R.string.chromium),"Cr",51.9961,24,1797,R.color.silver),
    MANGANESE(MainActivity.context.getResources().getString(R.string.manganese),"Mn",54.938,25,1774,R.color.silver),
    IRON(MainActivity.context.getResources().getString(R.string.iron),"Fe",55.845,26,-2000,R.color.gray),
    COBALT(MainActivity.context.getResources().getString(R.string.cobalt),"Co",58.6934,27,1735,R.color.gray),
    NICKEL(MainActivity.context.getResources().getString(R.string.nickel),"Ni",58.6934,28,1751,R.color.gray),
    COPPER(MainActivity.context.getResources().getString(R.string.copper),"Cu",63.546,29,-8000,R.color.copper),
    ZINC(MainActivity.context.getResources().getString(R.string.zinc),"Zn",65.39,30,1500,R.color.slate_gray),
    GALLIUM(MainActivity.context.getResources().getString(R.string.gallium),"Ga",69.723,31,1875,R.color.silver),
    GERMANIUM(MainActivity.context.getResources().getString(R.string.germanium),"Ge",72.64,32,1886,R.color.gray),
    ARSENIC(MainActivity.context.getResources().getString(R.string.arsenic),"As",74.9216,33,1250,R.color.silver),
    SELENIUM(MainActivity.context.getResources().getString(R.string.selenium),"Se",78.96,34,1817,R.color.gray),
    BROMINE(MainActivity.context.getResources().getString(R.string.bromine),"Br",79.904,35,1826,R.color.red),
    KRYPTON(MainActivity.context.getResources().getString(R.string.krypton),"Kr",83.8,36,1898,null),
    RUBIDIUM(MainActivity.context.getResources().getString(R.string.rubidium),"Rb",85.4678,37,1861,R.color.silver),
    STRONTIUM(MainActivity.context.getResources().getString(R.string.strontium),"Sr",87.62,38,1790,R.color.silver),
    YTTRIUM(MainActivity.context.getResources().getString(R.string.yttrium),"Y",88.9095,39,1794,R.color.silver),
    ZIRCONIUM(MainActivity.context.getResources().getString(R.string.zirconium),"Zr",91.224,40,1789,R.color.silver),
    NIOBIUM(MainActivity.context.getResources().getString(R.string.niobium),"Nb",92.9064,41,1801,R.color.gray),
    MOLYBDENUM(MainActivity.context.getResources().getString(R.string.molybdenum),"Mo",95.94,42,1781,R.color.gray),
    TECHNETIUM(MainActivity.context.getResources().getString(R.string.technetium),"Tc",98.0,43,1937,R.color.silver),
    RUTHENIUM(MainActivity.context.getResources().getString(R.string.ruthenium),"Ru",101.07,44,1844,R.color.silver),
    RHODIUM(MainActivity.context.getResources().getString(R.string.rhodium),"Rh",102.9055,45,1803,R.color.silver),
    PALLADIUM(MainActivity.context.getResources().getString(R.string.palladium),"Pd",106.42,46,1803,R.color.silver),
    SILVER(MainActivity.context.getResources().getString(R.string.silver),"Ag",107.8682,47,-3000,R.color.silver),
    CADMIUM(MainActivity.context.getResources().getString(R.string.cadmium),"Cd",112.411,48,1817,R.color.silver),
    INDIUM(MainActivity.context.getResources().getString(R.string.indium),"In",114.818,49,1863,R.color.silver),
    TIN(MainActivity.context.getResources().getString(R.string.tin),"Sn",118.71,50,-3000,R.color.silver),
    ANTIMONY(MainActivity.context.getResources().getString(R.string.antimony),"Sb",121.76,51,-3000,R.color.silver),
    TELLURIUM(MainActivity.context.getResources().getString(R.string.tellurium),"Te",127.6,52,1783,R.color.silver),
    IODINE(MainActivity.context.getResources().getString(R.string.iodine),"I",126.9045,53,1811,R.color.slate_gray),
    XENON(MainActivity.context.getResources().getString(R.string.xenon),"Xe",131.293,54,1898,null),
    CESIUM(MainActivity.context.getResources().getString(R.string.cesium),"Cs",132.9055,55,1860,R.color.silver),
    BARIUM(MainActivity.context.getResources().getString(R.string.barium),"Ba",137.327,56,1808,R.color.silver),
    LANTHANUM(MainActivity.context.getResources().getString(R.string.lanthanum),"La",138.9055,57,1839,R.color.silver),
    CERIUM(MainActivity.context.getResources().getString(R.string.cerium),"Ce",140.116,58,1803,R.color.silver),
    PRASEODYMIUM(MainActivity.context.getResources().getString(R.string.praseodymium),"Pr",140.9077,59,1885,R.color.silver),
    NEODYMIUM(MainActivity.context.getResources().getString(R.string.neodymium),"Nd",144.24,60,1885,R.color.silver),
    PROMETHIUM(MainActivity.context.getResources().getString(R.string.promethium),"Pm",(double)145,61,1945,R.color.silver),
    SAMARIUM(MainActivity.context.getResources().getString(R.string.samarium),"Sm",150.36,62,1879,R.color.silver),
    EUROPIUM(MainActivity.context.getResources().getString(R.string.europium),"Eu",151.964,63,1901,R.color.silver),
    GADOLINIUM(MainActivity.context.getResources().getString(R.string.gadolinium),"Gd",157.25,64,1880,R.color.silver),
    TERBIUM(MainActivity.context.getResources().getString(R.string.terbium),"Tb",158.9253,65,1834,R.color.silver),
    DYSPROSIUM(MainActivity.context.getResources().getString(R.string.dysprosium),"Dy",162.5,66,1886,R.color.silver),
    HOLMIUM(MainActivity.context.getResources().getString(R.string.holmium),"Ho",164.9303,67,1878,R.color.silver),
    ERBIUM(MainActivity.context.getResources().getString(R.string.erbium),"Er",167.259,68,1842,R.color.silver),
    THULIUM(MainActivity.context.getResources().getString(R.string.thulium),"Tm",168.9342,69,1879,R.color.silver),
    YTTERBIUM(MainActivity.context.getResources().getString(R.string.ytterbium),"Yb",173.04,70,1878,R.color.silver),
    LUTETIUM(MainActivity.context.getResources().getString(R.string.lutetium),"Lu",174.967,71,1907,R.color.silver),
    HAFNIUM(MainActivity.context.getResources().getString(R.string.hafnium),"Hf",178.49,72,1923,R.color.gray),
    TANTALUM(MainActivity.context.getResources().getString(R.string.tantalum),"Ta",180.9479,73,1802,R.color.gray),
    TUNGSTEN(MainActivity.context.getResources().getString(R.string.tungsten),"W",183.84,74,1783,R.color.gray),
    RHENIUM(MainActivity.context.getResources().getString(R.string.rhenium),"Re",186.207,75,1925,R.color.gray),
    OSMIUM(MainActivity.context.getResources().getString(R.string.osmium),"Os",190.23,76,1803,R.color.slate_gray),
    IRIDIUM(MainActivity.context.getResources().getString(R.string.iridium),"Ir",192.217,77,1803,R.color.silver),
    PLATINUM(MainActivity.context.getResources().getString(R.string.platinum),"Pt",195.078,78,1735,R.color.gray),
    GOLD(MainActivity.context.getResources().getString(R.string.gold),"Au",196.9665,79,-2500,R.color.gold),
    MERCURY(MainActivity.context.getResources().getString(R.string.mercury),"Hg",200.59,80,-1500,R.color.silver),
    THALLIUM(MainActivity.context.getResources().getString(R.string.thallium),"Tl",204.3833,81,1861,R.color.silver),
    LEAD(MainActivity.context.getResources().getString(R.string.lead),"Pb",207.2,82,-4000,R.color.slate_gray),
    BISMUTH(MainActivity.context.getResources().getString(R.string.bismuth),"Bi",208.9804,83,1400,R.color.gray),
    POLONIUM(MainActivity.context.getResources().getString(R.string.polonium), "Po", 209.0, 84,1898,R.color.silver),
    ASTATINE(MainActivity.context.getResources().getString(R.string.astatine), "At", 210.0, 85,1940,R.color.silver),
    RADON(MainActivity.context.getResources().getString(R.string.radon),"Rn",222.0,86,1900,null),
    FRANCIUM(MainActivity.context.getResources().getString(R.string.francium),"Fr",223.0,87,1939,R.color.silver),
    RADIUM(MainActivity.context.getResources().getString(R.string.radium),"Ra",226.0,88,1898,R.color.silver),
    ACTINIUM(MainActivity.context.getResources().getString(R.string.actinium),"Ac",227.0,89,1899,R.color.silver),
    THORIUM(MainActivity.context.getResources().getString(R.string.thorium),"Th",232.0381,90,1829,R.color.silver),
    PROTACTINIUM(MainActivity.context.getResources().getString(R.string.protactinium),"Pa",231.0359,91,1913,R.color.silver),
    URANIUM(MainActivity.context.getResources().getString(R.string.uranium),"U",238.0289,92,1789,R.color.silver),
    NEPTUNIUM(MainActivity.context.getResources().getString(R.string.neptunium),"Np",237.0,93,1940,R.color.silver),
    PLUTONIUM(MainActivity.context.getResources().getString(R.string.plutonium),"Pu",244.0,94,1940,R.color.silver),
    AMERICIUM(MainActivity.context.getResources().getString(R.string.americium),"Am",243.0,95,1944,R.color.silver),
    CURIUM(MainActivity.context.getResources().getString(R.string.curium),"Cm",247.0,96,1944,R.color.silver),
    BERKELIUM(MainActivity.context.getResources().getString(R.string.berkelium),"Bk",247.0,97,1949,null),
    CALIFORNIUM(MainActivity.context.getResources().getString(R.string.californium),"Cf",251.0,98,1950,null),
    EINSTEINIUM(MainActivity.context.getResources().getString(R.string.einsteinium),"Es",252.0,99,1952,null),
    FERMIUM(MainActivity.context.getResources().getString(R.string.fermium),"Fm",257.0,100,1952,null),
    MENDELEVIUM(MainActivity.context.getResources().getString(R.string.mendelevium),"Md",258.0,101,1955,null),
    NOBELIUM(MainActivity.context.getResources().getString(R.string.nobelium),"No",259.0,102,1958,null),
    LAWRENCIUM(MainActivity.context.getResources().getString(R.string.lawrencium),"Lr",262.0,103,1961,null),
    RUTHERFORDIUM(MainActivity.context.getResources().getString(R.string.rutherfordium),"Rf",261.0,104,1964,null),
    DUBNIUM(MainActivity.context.getResources().getString(R.string.dubnium),"Db",262.0,105,1967,null),
    SEABORGIUM(MainActivity.context.getResources().getString(R.string.seaborgium),"Sg",266.0,106,1974,null),
    BOHRIUM(MainActivity.context.getResources().getString(R.string.bohrium),"Bh",264.0,107,1981,null),
    HASSIUM(MainActivity.context.getResources().getString(R.string.hassium),"Hs",277.0,108,1984,null),
    MEITNERIUM(MainActivity.context.getResources().getString(R.string.meitnerium),"Mt",269.0,109,1982,null),
    DARMSTADTIUM(MainActivity.context.getResources().getString(R.string.darmstadtium),"Ds",281.0,110,1994,null),
    ROENTGENIUM(MainActivity.context.getResources().getString(R.string.roentgenium),"Rg",281.0,111,1994,null),
    COPERNICIUM(MainActivity.context.getResources().getString(R.string.copernicium),"Cn",285.0,112,1996,null),
    UNUNTRIUM(MainActivity.context.getResources().getString(R.string.ununtrium),"Uut",286.0,113,2004,null),
    FLEROVIUM(MainActivity.context.getResources().getString(R.string.flerovium),"Fl",289.0,114,1998,null),
    UNUNPENTIUM(MainActivity.context.getResources().getString(R.string.ununpentium),"Uup",289.0,115,2004,null),
    LIVERMORIUM(MainActivity.context.getResources().getString(R.string.livermorium),"Lv",293.0,116,200,null),
    UNUNSEPTIUM(MainActivity.context.getResources().getString(R.string.ununseptium),"Uus",294.0,117,0,null),
    UNUNOCTIUM(MainActivity.context.getResources().getString(R.string.ununoctium),"Uuo",294.0,118,2006,null);

    private String name,abbreviation;
    private Double mass;
    private int atomicNumber;
    private Integer color;

    private int discYear;

    public static final Double nA = 602214000000000000000000.0;

    Element(String name, String abbreviation, Double mass, Integer atomicNumber, Integer yearOfDiscovery, Integer color) {
        this.name = name;
        this.abbreviation = abbreviation;
        this.mass = mass;
        this.atomicNumber = atomicNumber;
        this.discYear = yearOfDiscovery;
        this.color = color;
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

    public static Element findElementByName(String name){
        for(Element currentElement : Element.values()){
            if(currentElement.getName().equalsIgnoreCase(name)) {
                return currentElement;
            }
        }

        return null;
    }

    public static Element findElementByAbbreviationOrName(String nameOrAbbreviation){
        Element foundElement;
        foundElement = Element.findElementByAbbreviation(nameOrAbbreviation);
        if(foundElement == null){
            foundElement = Element.findElementByName(nameOrAbbreviation);
        }

        return foundElement;
    }


    public ElementFragment toFragment(){
        return ElementFragment.newInstance(this);
    }


    public String getName() {
        return name;
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

    public String getDiscYearString() {
        if (this.discYear == 0){
            return MainActivity.context.getResources().getString(R.string.year_of_disc_unknown);
        } else if (this.discYear < 0) {
            return Integer.toString(0 - this.discYear) + " " + MainActivity.context.getResources().getString(R.string.before_christ);
        } else {
            return Integer.toString(this.discYear);
        }
    }

    public Integer getColor() {
        return this.color;
    }

}

