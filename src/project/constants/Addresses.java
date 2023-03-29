package project.constants;

// laptop address
// "192.168.56.1"
// default is ""

public enum Addresses {
    SCHEDULER("192.168.56.1"), ELEVATOR("192.168.56.1"), FLOOR("192.168.56.1"), DEFAULT("");

    private final String address;
    Addresses(String addr){
        this.address = addr; 
    }

    public String getAddress(){
        return this.address;
    }
}
