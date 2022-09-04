package MathGeo;


public abstract class AMS implements VerticalityStraightnessBuildable {

    DataPreparer dataPreparer;
    //Operator nameOperator;
    //String numberBS;
    //String address;
    TypeAMS type;
    //int height;


    public AMS(DataPreparer dataPreparer, /*Operator operator, String numberBS, String address,*/ TypeAMS type/*, int height*/) {
        this.dataPreparer = dataPreparer;
        //this.nameOperator = operator;
        //this.numberBS = numberBS;
        //this.address = address;
        this.type = type;
        //this.height = height;
    }

}

