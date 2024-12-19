public class Employee {
    private String name;
    private String position;
    private double hourlyWage;
    private double hoursWorked;
    private User user;

    public Employee(String name, String position, double hourlyWage, User user) {
        this.name = name;
        this.position = position;
        this.hourlyWage = hourlyWage;
        this.hoursWorked = 0; 
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public double getHourlyWage() {
        return hourlyWage;
    }

    public double getHoursWorked() {
        return hoursWorked;
    }

    public void logHours(double hours) {
        this.hoursWorked += hours;
    }

    public double calculateWage() {
        if(hoursWorked>40) {
        	return 40*hourlyWage + (hoursWorked-40)*hourlyWage*1.5;
        }
    	return hoursWorked * hourlyWage;
    }

    public double getTotalEarnings() {
        return calculateWage();
    }

    public User getUser () {
        return user;
    }
    
    @Override

    public String toString() {

        return this.name;

    }
}