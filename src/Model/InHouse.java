package Model;

/**
 *
 * @author antonio
 */
public class InHouse extends Part {
    private int machineId;
    
    /**
     * InHouse Constructor
     * @param id id to assign
     * @param name name to assign
     * @param price price to assign
     * @param stock stock to assign
     * @param min min to assign
     * @param max max to assign
     * @param machineId machine id to assign
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId ) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }
    /**
     * @param machineId to machineId to set
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
    
    /**
     * @return the machineId
     */
    public int getMachineId() {
        return machineId;
    }
}
    
    



