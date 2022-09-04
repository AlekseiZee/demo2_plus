public abstract class AMS {

    VerticalityStraightnessCountAble verticalityStraightnessCountAble;
    String operator;
    String numberBS;
    String address;
    String type;
    int height;


    public AMS(VerticalityStraightnessCountAble verticalityStraightnessCountAble, String operator, String numberBS,
               String address, String type, int height) {
        this.verticalityStraightnessCountAble = verticalityStraightnessCountAble;
        this.operator = operator;
        this.numberBS = numberBS;
        this.address = address;
        this.type = type;
        this.height = height;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getNumberBS() {
        return numberBS;
    }

    public void setNumberBS(String numberBS) {
        this.numberBS = numberBS;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}

