package project.constants;

// laptop address
// "192.168.56.1"
// default is ""

public enum Addresses {
    DEFAULT(""), SCHEDULER(Addresses.DEFAULT.getAddress()), ELEVATOR(Addresses.DEFAULT.getAddress()), FLOOR(Addresses.DEFAULT.getAddress());

    private final String address;
    Addresses(String addr){
        this.address = addr; 
    }

    public String getAddress(){
        return this.address;
    }
}
