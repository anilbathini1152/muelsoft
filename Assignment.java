import java.sql.*;
import java.util.Scanner;
class DBOperations{
    public void insert(Connection con,Scanner sc) throws SQLException{
        
        String movie_name,actor,actress,year_of_release,dir_name;
        sc.nextLine();
        System.out.println("Enter the name of Movie");
        movie_name=sc.nextLine();
        System.out.println("Enter the name of actor");
        actor=sc.nextLine();
        System.out.println("Enter the name of actress");
        actress=sc.nextLine();
        System.out.println("Enter the year of release in format DD-MM-YYYY");
        year_of_release=sc.nextLine();
        System.out.println("Enter the Director Name");
        dir_name=sc.nextLine();

        String qr="insert into movies values(?,?,?,?,?)";
        PreparedStatement stmt=con.prepareStatement(qr);
        stmt.setString(1, movie_name);
        stmt.setString(2, actor);
        stmt.setString(3, actress);
        stmt.setString(4, year_of_release);
        stmt.setString(5, dir_name);

        if(stmt.executeUpdate()!=0){
            System.out.println("Insertion Successfull");
        }
        else{
            System.out.println("Insertion Failed");
        }
       
    }

    public void printDetails(ResultSet rs)throws SQLException{
        // System.out.println("Movie Name \t Actor \t Actress \t Y_OF_Release \t Director");
        while(rs.next()){
            System.out.println("Movie Name: "+rs.getString(1)+"\n Actor:"+rs.getString(2)+" \n Actress: "+rs.getString(3)+"\n Date of Release: "+rs.getString(4)+"\n Director: "+rs.getString(5));
            System.out.println("");
            System.out.println("--------------------------------------------------------------------------------------");
        }
    }
    public void retrieveAll(Connection con)throws SQLException{
        String qr="select * from movies";
        Statement st=con.createStatement();
        ResultSet rs=st.executeQuery(qr);
        this.printDetails(rs);
    }
    public void retrieveByActors(Connection con,Scanner sc)throws SQLException{
        sc.nextLine();
        System.out.println("Enter the name of actor");
        String a=sc.nextLine();
        String qr="select * from movies where actor='"+a+"'";
        Statement st=con.createStatement();
        // st.setString(1, a);
        ResultSet rs=st.executeQuery(qr);
        this.printDetails(rs);
    }
}
class Assignment{
    public static void main(String args[]) {
        
        try{
            DBOperations obj=new DBOperations();
            Scanner sc=new Scanner(System.in);

            int sw;
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
            if(con==null){
                System.out.println("Error");
            }
            else{
                while(true){
                    System.out.println("Enter 1 for inserting \n Enter 2 for Retrieving all values \n Enter 3 for retrieving the values by actor name \n Enter any other no to exit");
                    sw=sc.nextInt();
                    if(sw==1){
                        obj.insert(con,sc);
                    }
                    else if(sw==2){
                        obj.retrieveAll(con);
                    }
                    else if(sw==3){
                        obj.retrieveByActors(con, sc);
                    }
                    else {
                        break;
                }
            }
            sc.close();
        }
    }
        catch(Exception e){
            System.out.println(e);
        }
        
    }
}
