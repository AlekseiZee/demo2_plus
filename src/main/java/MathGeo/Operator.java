package MathGeo;

public enum Operator {
    MEGAFON("ПАО \\\"МегаФон\\\""),
    MTS("ПАО МТС"),
    TELE2("АО  \\\"Теле2-Санкт-Петербург\\\""),
    BILIN("ПАО \\\"ВымпелКом\\\""),
    RUSSIAN_TOWERS("АО \\\"РУССКИЕ БАШНИ\\\""),
    LINK_DEVELOPMENT("ООО \\\"Линк Девелопмент\\\"");

    String name;

    Operator(String name) {
        this.name = name;
    }
}
