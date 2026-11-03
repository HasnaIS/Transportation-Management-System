//Abstract class representing a general person in the system (rider or driver)
public abstract class Person implements IPerson {

   private final int id;

   private String name;

   private String phoneNumber;

   private final LinkedList<IRide> rideHistory =
            new LinkedList<>();

   public Person(
            int id,
            String name,
            String phoneNumber) {
   
      this.id = id;
   
      this.name = name;
   
      setPhoneNumber(phoneNumber);
   }

   @Override
    public int getId() {
      return id;
   }

   @Override
    public String getName() {
      return name;
   }

   @Override
    public void setName(String name) {
      this.name = name;
   }

   @Override
    public String getPhoneNumber() {
      return phoneNumber;
   }
//validates and sets the phone number(must be exactly 10 digits)
   @Override
    public void setPhoneNumber(
            String phoneNumber) {
   //check if phone number is null or not 10 digits long 
      if (phoneNumber == null || phoneNumber.length() != 10) {
         throw new IllegalArgumentException("Phone number must contain 10 digits.");
      }
   //ensure all characters are digits between '0' and '9'
      for (int i = 0; i < phoneNumber.length(); i++) {
         if (phoneNumber.charAt(i) < '0' || phoneNumber.charAt(i) > '9') {
            throw new IllegalArgumentException("Phone number must contain digits only.");
         }
      }
   
      this.phoneNumber = phoneNumber;
   }

   @Override
    public LinkedList<IRide> getRideHistory() {
      return rideHistory;
   }

   @Override
    public String toString() {
   
      return "ID: " + id +
                ", Name: " + name +
                ", Phone: " + phoneNumber;
   }
}