//represents a rider in the ride-sharing system
public class Rider
        extends Person
        implements IRider {

   private String email;

   private String homeCity;

   public Rider(
           int id,
           String name,
           String email,
           String phoneNumber,
           String homeCity) {
   
      super(
             id,
             name,
             phoneNumber
         );
   
      this.email = email;
   
      this.homeCity = homeCity;
   }

   @Override
   public String getEmail() {
      return email;
   }

   @Override
   public void setEmail(String email) {
      this.email = email;
   }

   @Override
   public String getHomeCity() {
      return homeCity;
   }

   @Override
   public void setHomeCity(
           String homeCity) {
   
      this.homeCity = homeCity;
   }
//compares riders by ID
   @Override
   public int compareTo(IRider other) {
   
      if (getId() < other.getId()) {
         return -1;
      } else if (getId() > other.getId()) {
         return 1;
      } else {
         return 0;
      }
   }

   @Override
   public String toString() {
   
      return "Rider{" +
             "ID=" + getId() +
             ", Name='" + getName() + '\'' +
             ", Email='" + email + '\'' +
             ", Phone='" + getPhoneNumber() + '\'' +
             ", HomeCity='" + homeCity + '\'' +
             '}';
   }
}