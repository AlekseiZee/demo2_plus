package MathGeo;

public enum TypeAMS {
    TOWER("Башня"),
    MAST("Мачта"),
    METAL_POLE("Столб металлический"),
    REINFORCED_CONCRETE_POLE("Столб железобетонный"),
    TRIPOD("Трипод"),
    DUP("ОДН");

    String type;

    TypeAMS(String type) {
        this.type = type;
    }
}
