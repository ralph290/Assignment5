import java.util.Scanner;

public class Date {
    private int day;
    private int month;
    private int year;

    public Date() {
        this.day = 1;
        this.month = 1;
        this.year = 2000;
    }

    public Date(int day, int month, int year) throws IllegalArgumentException {
        setDayMonthYear(day, month, year);
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public void setDayMonthYear(int day, int month, int year) throws IllegalArgumentException {
        if (day < 1 || month < 1 || year < 1) {
            throw new IllegalArgumentException("Day, month and year must be positive integers");
        }
        if (month > 12) {
            throw new IllegalArgumentException("Month must be between 1 and 12");
        }
        if (day > 31) {
            throw new IllegalArgumentException("Day must be between 1 and 31");
        }
        if ((month == 4 || month == 6 || month == 9 || month == 11) && day > 30) {
            throw new IllegalArgumentException("Month " + month + " has 30 days only");
        }
        if (month == 2) {
            boolean isLeapYear = (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
            if (isLeapYear && day > 29) {
                throw new IllegalArgumentException("February has 29 days only in leap years");
            } else if (!isLeapYear && day > 28) {
                throw new IllegalArgumentException("February has 28 days only in non-leap years");
            }
        }
        this.day = day;
        this.month = month;
        this.year = year;
    }
	

    public boolean isLeapYear(int year) {
        return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0);
    }
	
    public String getDayOfWeek() {
        int d = day;
        int m = month;
        int y = year;
        int t[] = { 0, 3, 2, 5, 0, 3, 5, 1, 4, 6, 2, 4 };
        y -= (m < 3) ? 1 : 0;
        int dayOfWeek = (y + y/4 - y/100 + y/400 + t[m-1] + d) % 7;
        String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        return days[dayOfWeek];
    }

	public int differenceInDays(Date otherDate) {
    int thisDays = day;
    for (int i = 1; i < month; i++) {
        if (i == 4 || i == 6 || i == 9 || i == 11) {
            thisDays += 30;
        } else if (i == 2) {
            if (isLeapYear()) {
                thisDays += 29;
            } else {
                thisDays += 28;
            }
        } else {
            thisDays += 31;
        }
    }
    thisDays += 365 * (year - 1) + countLeapYears(year - 1);
    int otherDays = otherDate.getDay();
    for (int i = 1; i < otherDate.getMonth(); i++) {
        if (i == 4 || i == 6 || i == 9 || i == 11) {
            otherDays += 30;
        } else if (i == 2) {
            if (otherDate.isLeapYear()) {
                otherDays += 29;
            } else {
                otherDays += 28;
            }
        } else {
            otherDays += 31;
        }
    }
    otherDays += 365 * (otherDate.getYear() - 1) + countLeapYears(otherDate.getYear() - 1);
    return Math.abs(thisDays - otherDays);
}

    public String toString() {
        return String.format("%02d/%02d/%04d", day, month, year);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Date)) {
            return false;
        }
        Date other = (Date) obj;
        return this.day == other.day && this.month == other.month && this.year == other.year;
    }

    @Override
    public int hashCode() {
        return Objects.hash(day, month, year);
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the first date in dd/mm/yyyy format:");
        String firstDateStr = scanner.nextLine();
        Date firstDate = Date.parseDate(firstDateStr);
        System.out.println("The day of the week for the first date is " + firstDate.getDayOfWeek());

        System.out.println("Enter the second date in dd/mm/yyyy format:");
        String secondDateStr = scanner.nextLine();
        Date secondDate = Date.parseDate(secondDateStr);

        int differenceInDays = firstDate.differenceInDays(secondDate);
        System.out.println("The difference between the two dates is " + differenceInDays + " days.");


    }


	

    
}