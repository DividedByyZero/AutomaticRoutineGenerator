
import java.awt.Color;
import java.awt.Toolkit;
import static java.lang.Integer.parseInt;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.Collections;
import java.util.Random;
import javafx.util.Pair;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author Hp
 */
public class Dashboard extends javax.swing.JFrame {

    /**
     * Code For Automatic Routine
     */
// Sorting Professor
    public static String[][] list = new String[3][6];
// Variable
    public String Title = "Jatiya Kabi Kazi Nazrul Islam University";
    public String Sign = "(Head of The Dept.)";
//Maps
    static Map<String,Session> S = new HashMap<String,Session>();
    static Map<String,Course> CR = new HashMap<String,Course>();
    static Map<String,Room> R = new HashMap<String,Room>();
    static Map<String,Professor> PR = new HashMap<String,Professor>();
//Lists
    static ArrayList<String> SessionList = new ArrayList<String>();
    static ArrayList<String> CourseList = new ArrayList<String>();
    static ArrayList<String> SlotList = new ArrayList<String>();
    static ArrayList<String> DayList = new ArrayList<String>();
    static ArrayList<String> ClassRoomList = new ArrayList<String>();
    static ArrayList<String> LabRoomList = new ArrayList<String>();
    static ArrayList<String> ProfessorList = new ArrayList<String>();
    static ArrayList<ArrayList<String> > AssignList =  new ArrayList<ArrayList<String> >();
//Function
    // Welcome Note
    // Collect data from database
    public static void getData(){
        DatabaseConnection cn = new DatabaseConnection();
        Connection connection = cn.getConnection();
        //Slot Query
        String query = "select distinct * from slot";
        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                String slot = resultSet.getString("slot_name");
                SlotList.add(slot);
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        // Day Query
        query = "select distinct * from day";
        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                String day = resultSet.getString("day_name");
                DayList.add(day);
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        // Room Query
        query = "select distinct * from room";
        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                String room = resultSet.getString("room_name");
                String category = resultSet.getString("Category");
                if(category.equals("Class Room"))ClassRoomList.add(room);
                else LabRoomList.add(room);
                Room r = new Room(room,category);
                r.setSize(DayList.size(),SlotList.size());
                R.put(room, r);
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        // Session Query
        query = "select distinct * from session";
        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                String session = resultSet.getString("Name");
                SessionList.add(session);
                Session s = new Session(session);
                s.setSize(DayList.size(), SlotList.size());
                S.put(session, s);
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        //Course Query
        query = "select distinct * from course";
        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                String course_name = resultSet.getString("course_name");
                String course_code = resultSet.getString("course_code");
                String course_slot = resultSet.getString("course_slot");
                String course_perweekclass = resultSet.getString("course_perweekclass");
                String course_session = resultSet.getString("course_session");
                CourseList.add(course_code);
                Course x = new Course(course_name,course_code,course_slot,course_perweekclass,course_session);
                CR.put(course_code, x);
                S.get(course_session).Courses.add(course_code);
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        //CourseRoom 
        
        query = "select distinct * from courseroom";
        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                String code = resultSet.getString("course_code");
                String room = resultSet.getString("course_room");
                System.out.println(CR);
                CR.get(code).roomlist.add(room);
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        
        
        //
        // Professor Query
        query = "select distinct * from professor";
        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                String professor_name = resultSet.getString("professor_name");
                String professor_code = resultSet.getString("professor_code");
                String professor_number = resultSet.getString("professor_number");
                String professor_jobyear = resultSet.getString("professor_jobyear");
                ProfessorList.add(professor_code);
                Professor x = new Professor(professor_name,professor_code,professor_number,professor_jobyear);
                x.setSize(DayList.size(), SlotList.size());
                PR.put(professor_code, x);
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    public static void sortCourse(){
        for(int i=0;i<CourseList.size()-1;i++){
            int value1 = Integer.parseInt(CR.get(CourseList.get(i)).Slots);
            int track = i;
            for(int j=i+1;j<CourseList.size();j++){
                int value2 = Integer.parseInt(CR.get(CourseList.get(j)).Slots);
                if(value1<value2){
                    track = j;
                }
            }
            Collections.swap(CourseList, i, track);
        }
        System.out.println(CourseList);
    }
    public static void sortProfessor(){
        for(int i=0;i<ProfessorList.size()-1;i++){
            int value1 = Integer.parseInt(PR.get(ProfessorList.get(i)).jobyear);
            int track = i;
            for(int j=i+1;j<ProfessorList.size();j++){
                int value2 = Integer.parseInt(PR.get(ProfessorList.get(j)).jobyear);
                if(value1<value2){
                    track = j;
                }
            }
            Collections.swap(ProfessorList, i, track);
        }
        System.out.println(ProfessorList);
    }
    public static void setList(){
        double rr = ProfessorList.size()/3;
        int r = (int) Math.ceil(rr);
        System.out.print(r);
        int count = 0;
        for(int col = 0;col<3;col++){
            for(int row = 0;row<r;row++){
                list[row][col] = ProfessorList.get(count);
                count++;
                if(count==ProfessorList.size()) break;
            }
            if(count==ProfessorList.size()) break;
        }
        for(int row = 0;row<r;row++){
            for(int col = 0;col<3;col++){
                System.out.print(list[row][col]+ " ");
            }
            System.out.println();
        }
        
    }
    //Find Slot
    public static ArrayList<String> FindSlot(String prof,String session,String course){
        System.out.println(prof);
        System.out.println(PR);
        System.out.println(session);
        System.out.println(S);
        System.out.println(course);
        System.out.println(CR);
        ArrayList<String> ans = new ArrayList<String>();
        ans.add("-1:");
        ans.add("-1");
        ans.add("-1");
        ArrayList<Integer> axisX = new ArrayList<Integer>();
        ArrayList<Integer> axisY = new ArrayList<Integer>();
        ArrayList<String> roomname = new ArrayList<String>();
        Session ss = S.get(session);
        Professor pp = PR.get(prof);
        Course cc = CR.get(course);
        if(cc.roomlist.size() != 0){
            Collections.shuffle(cc.roomlist);
            for(int i=0;i<DayList.size();i++){
                if(ss.Track.containsKey(i)){
                    if(ss.Track.get(i).contains(pp.code)){
                        continue;
                    }
                }
                for(int j=0;j<SlotList.size();j++){
                    for(String r : cc.roomlist){
                        Room rr = R.get(r);
                        Boolean flag = true;
                        for(int check=0;check<parseInt(cc.Slots);check++){
                            if(j+check >= SlotList.size()){
                                flag = false;
                                break;
                            }
                            if(ss.routine[i][j+check]!=null || pp.routine[i][j+check]!=null || rr.routine[i][j+check]!=null){
                                flag = false;
                                break;
                            }
                        }
                        if(flag){
                            axisX.add(i);
                            axisY.add(j);
                            roomname.add(rr.name);
                        }
                    }
                }
            }
        }
        else{
            for(int i=0;i<DayList.size();i++){
                if(ss.Track.containsKey(i)){
                    if(ss.Track.get(i).contains(pp.code)){
                        continue;
                    }
                }
                for(int j=0;j<SlotList.size();j++){
                    for(String r : ClassRoomList){
                        Room rr = R.get(r);
                        Boolean flag = true;
                        for(int check=0;check<parseInt(cc.Slots);check++){
                            if(j+check >= SlotList.size()){
                                flag = false;
                                break;
                            }
                            if(ss.routine[i][j+check]!=null || pp.routine[i][j+check]!=null || rr.routine[i][j+check]!=null){
                                flag = false;
                                break;
                            }
                        }
                        if(flag){
                            axisX.add(i);
                            axisY.add(j);
                            roomname.add(rr.name);
                        }
                    }
                }
            }
        }
        if(axisX.size()==0)return ans;
        int sz = axisX.size();
        Random random = new Random();
        int select = random.nextInt(sz);
        ans.set(0, String.valueOf(axisX.get(select)));
        ans.set(1, String.valueOf(axisY.get(select)));
        ans.set(2, roomname.get(select));
        return ans;
    }
    // Show All Routine
    
    public static void allRoutine(){
        for(String session : SessionList){
            Session ss = S.get(session);
            System.out.println("=============================================================");
            System.out.println(ss.name+" Routine ");
            System.out.println("=============================================================");
            ss.ShowRoutine();
            System.out.println("=============================================================");
        }
        for(String professor : ProfessorList){
            Professor pp = PR.get(professor);
            System.out.println("=============================================================");
            System.out.println(pp.name+" Routine ");
            System.out.println("=============================================================");
            pp.ShowRoutine();
            System.out.println("=============================================================");
        }
    }
    //
    public static void clearAllRoutine(){
        for(String session : SessionList){
            S.get(session).ClearRoutine();
            S.get(session).Track.clear();
        }
        for(String professor : ProfessorList){
            PR.get(professor).ClearRoutine();
        }
    }
    //Generate Routine
    public static void Generate(){
        clearAllRoutine();
        sortCourse();
        sortProfessor();
        setList();
        ArrayList<String> FirstList = new ArrayList<String>();
        ArrayList<String> SecondList = new ArrayList<String>();
        for(int i=0;i<CourseList.size();i++){
            int value = Integer.parseInt(CR.get(CourseList.get(i)).Slots);
            if(value>1)FirstList.add(CourseList.get(i));
            else SecondList.add(CourseList.get(i));
        }
        Collections.shuffle(SecondList);
        FirstList.addAll(SecondList);
        for(String course_name : FirstList){
            Course c = CR.get(course_name);
            for(int total = 0 ;total <parseInt(c.PerWeek);total++){
                String session = CR.get(course_name).Session;
                String professor_code = CR.get(course_name).ProfessorCode;
                Session ss = S.get(session);
                Professor pp = PR.get(professor_code);
                ArrayList<String> pos = FindSlot(professor_code,session,course_name);
                if(pos.get(0).equals("-1") || pos.get(1).equals("-1") ){
                    System.out.println("Not Possible! " + course_name);
                    break;
                }
                int row = parseInt(pos.get(0));
                int col = parseInt(pos.get(1));
                String roomname = pos.get(2);
                if(ss.Track.containsKey(row)){
                    ss.Track.get(row).add(professor_code);
                }
                else{
                    ArrayList<String> a = new ArrayList<String>();
                    a.add(professor_code);
                    ss.Track.put(row, a);
                }
                
                int slot = parseInt(c.Slots);
                String putSession = c.Code+"("+professor_code+")";
                String putProfessor = c.Code + "(" + session+")";
                if(LabRoomList.contains(roomname)){
                    putSession = putSession + " " + roomname;
                    putProfessor = putProfessor + " " + roomname;
                }
                for(int i=0;i<slot;i++){
                    ss.routine[row][col+i] = putSession;
                    pp.routine[row][col+i] = putProfessor;
                }
            }
            c.finish = true;
        }
        allRoutine();
    }
    public Dashboard() {
        initComponents();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("icon.png")));
        this.setTitle("Automatic Routine Generator");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        SaveAndExit = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        Menu = new javax.swing.JTabbedPane();
        Main = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        CustomizeRoutine = new javax.swing.JPanel();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        Busy = new javax.swing.JButton();
        AddSession = new javax.swing.JPanel();
        SessionName = new javax.swing.JTextField();
        SessionAdd = new javax.swing.JButton();
        jLabel44 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        Add1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        SessionTable = new javax.swing.JTable();
        Add2 = new javax.swing.JButton();
        AddProfessor = new javax.swing.JPanel();
        ProfessorName = new javax.swing.JTextField();
        professorNumber = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        ProfessorNumber = new javax.swing.JTextField();
        ProfessorCode = new javax.swing.JTextField();
        professorNumber2 = new javax.swing.JLabel();
        DeleteProfessor = new javax.swing.JButton();
        ProfessorAdd = new javax.swing.JButton();
        ProfessorUpdate = new javax.swing.JButton();
        jLabel45 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        ProfessorTable = new javax.swing.JTable();
        JobYear = new javax.swing.JTextField();
        jLabel48 = new javax.swing.JLabel();
        AddCourse = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        CourseName = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        CourseCode = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        SessionComboBox = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        Add_Course = new javax.swing.JButton();
        UpdateCourse = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        CourseTable = new javax.swing.JTable();
        DeleteCourse = new javax.swing.JButton();
        SlotComboBox = new javax.swing.JComboBox<>();
        jLabel24 = new javax.swing.JLabel();
        WeekComboBox = new javax.swing.JComboBox<>();
        jLabel27 = new javax.swing.JLabel();
        AssignRoutine = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        AssignSessionComboBox = new javax.swing.JComboBox<>();
        AssignCourseComboBox = new javax.swing.JComboBox<>();
        AssignProfessorComboBox = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        SearchCourse = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        AssignTable = new javax.swing.JTable();
        jButton8 = new javax.swing.JButton();
        Slot = new javax.swing.JPanel();
        SlotName = new javax.swing.JTextField();
        Add3 = new javax.swing.JButton();
        Add4 = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        SlotTable = new javax.swing.JTable();
        Add5 = new javax.swing.JButton();
        Day = new javax.swing.JPanel();
        DayName = new javax.swing.JTextField();
        AddDay = new javax.swing.JButton();
        UpdateDay = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        DayTable = new javax.swing.JTable();
        DeleteDay = new javax.swing.JButton();
        Room = new javax.swing.JPanel();
        RoomName = new javax.swing.JTextField();
        AddRoom = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        RoomTable = new javax.swing.JTable();
        DeleteRoom = new javax.swing.JButton();
        RoomCategoryComboBox = new javax.swing.JComboBox<>();
        CourseRoom = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        RoomComboBox = new javax.swing.JComboBox<>();
        CourseComboBox = new javax.swing.JComboBox<>();
        jLabel23 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        CourseRoomTable = new javax.swing.JTable();
        AddCourseRoom = new javax.swing.JButton();
        DeleteCourseRoom = new javax.swing.JButton();
        SetSignature = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        SignName = new javax.swing.JTextArea();
        Addsign = new javax.swing.JButton();
        jLabel25 = new javax.swing.JLabel();
        SetTitle = new javax.swing.JPanel();
        jScrollPane12 = new javax.swing.JScrollPane();
        TitleName = new javax.swing.JTextArea();
        Addtitle = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        AssignBusySchedule = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        CategoryComboBox = new javax.swing.JComboBox<>();
        SelectComboBox = new javax.swing.JComboBox<>();
        BusyDayComboBox = new javax.swing.JComboBox<>();
        jLabel28 = new javax.swing.JLabel();
        label001 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jButton15 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        BusySlotComboBox = new javax.swing.JComboBox<>();
        jLabel31 = new javax.swing.JLabel();
        Generate = new javax.swing.JPanel();
        jButton17 = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();
        jButton19 = new javax.swing.JButton();
        jButton21 = new javax.swing.JButton();
        jButton22 = new javax.swing.JButton();
        Busy1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 102, 0));
        setPreferredSize(new java.awt.Dimension(1653, 1000));

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(0, 153, 0));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon.png"))); // NOI18N
        jPanel4.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(226, 63, -1, -1));

        jLabel2.setFont(new java.awt.Font("Poor Richard", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/homework (1).png"))); // NOI18N
        jLabel2.setText("         Add Course");
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel2MouseExited(evt);
            }
        });
        jPanel4.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(-20, 380, 512, 73));

        jLabel3.setFont(new java.awt.Font("Poor Richard", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/engineering.png"))); // NOI18N
        jLabel3.setText("         Generate");
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel3MouseExited(evt);
            }
        });
        jPanel4.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(-70, 620, 580, 73));

        jLabel4.setFont(new java.awt.Font("Poor Richard", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/training.png"))); // NOI18N
        jLabel4.setText("         Add Session");
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel4MouseExited(evt);
            }
        });
        jPanel4.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 300, 450, 73));

        jLabel5.setFont(new java.awt.Font("Poor Richard", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pencil (1).png"))); // NOI18N
        jLabel5.setText("        Customize Routine");
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel5MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel5MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel5MousePressed(evt);
            }
        });
        jPanel4.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 220, 520, 73));

        SaveAndExit.setFont(new java.awt.Font("Poor Richard", 1, 24)); // NOI18N
        SaveAndExit.setForeground(new java.awt.Color(255, 255, 255));
        SaveAndExit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        SaveAndExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/exit (3).png"))); // NOI18N
        SaveAndExit.setText("        Save And Exit");
        SaveAndExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SaveAndExitMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                SaveAndExitMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                SaveAndExitMouseExited(evt);
            }
        });
        jPanel4.add(SaveAndExit, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 700, 470, 73));

        jLabel7.setFont(new java.awt.Font("Poor Richard", 1, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assignment.png"))); // NOI18N
        jLabel7.setText("       Assign Courses");
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel7MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel7MouseExited(evt);
            }
        });
        jPanel4.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 540, 512, 73));

        jLabel12.setFont(new java.awt.Font("Poor Richard", 1, 24)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/professor.png"))); // NOI18N
        jLabel12.setText("        Add Professor");
        jLabel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel12MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel12MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel12MouseExited(evt);
            }
        });
        jPanel4.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(-30, 460, 540, 73));

        jPanel3.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 950));

        Main.setBackground(new java.awt.Color(255, 102, 0));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rsz_11_1.png"))); // NOI18N

        javax.swing.GroupLayout MainLayout = new javax.swing.GroupLayout(Main);
        Main.setLayout(MainLayout);
        MainLayout.setHorizontalGroup(
            MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MainLayout.createSequentialGroup()
                .addContainerGap(569, Short.MAX_VALUE)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(284, 284, 284))
        );
        MainLayout.setVerticalGroup(
            MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MainLayout.createSequentialGroup()
                .addGap(177, 177, 177)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 525, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(753, Short.MAX_VALUE))
        );

        Menu.addTab("tab1", Main);

        CustomizeRoutine.setBackground(new java.awt.Color(255, 102, 0));

        jButton9.setBackground(new java.awt.Color(0, 102, 0));
        jButton9.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jButton9.setForeground(new java.awt.Color(255, 255, 255));
        jButton9.setText("Course Room");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton10.setBackground(new java.awt.Color(0, 102, 0));
        jButton10.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jButton10.setForeground(new java.awt.Color(255, 255, 255));
        jButton10.setText("Solts");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton11.setBackground(new java.awt.Color(0, 102, 0));
        jButton11.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jButton11.setForeground(new java.awt.Color(255, 255, 255));
        jButton11.setText("Days");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jButton12.setBackground(new java.awt.Color(0, 102, 0));
        jButton12.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jButton12.setForeground(new java.awt.Color(255, 255, 255));
        jButton12.setText("Rooms");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jButton13.setBackground(new java.awt.Color(0, 102, 0));
        jButton13.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jButton13.setForeground(new java.awt.Color(255, 255, 255));
        jButton13.setText("Set Title");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        jButton14.setBackground(new java.awt.Color(0, 102, 0));
        jButton14.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jButton14.setForeground(new java.awt.Color(255, 255, 255));
        jButton14.setText("Set Signature");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        Busy.setBackground(new java.awt.Color(0, 102, 0));
        Busy.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        Busy.setForeground(new java.awt.Color(255, 255, 255));
        Busy.setText("Set Busy Schedule");
        Busy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BusyActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout CustomizeRoutineLayout = new javax.swing.GroupLayout(CustomizeRoutine);
        CustomizeRoutine.setLayout(CustomizeRoutineLayout);
        CustomizeRoutineLayout.setHorizontalGroup(
            CustomizeRoutineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CustomizeRoutineLayout.createSequentialGroup()
                .addGap(202, 202, 202)
                .addGroup(CustomizeRoutineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Busy, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(CustomizeRoutineLayout.createSequentialGroup()
                        .addGroup(CustomizeRoutineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(39, 39, 39)
                        .addGroup(CustomizeRoutineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(37, 37, 37)
                        .addGroup(CustomizeRoutineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(522, Short.MAX_VALUE))
        );
        CustomizeRoutineLayout.setVerticalGroup(
            CustomizeRoutineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CustomizeRoutineLayout.createSequentialGroup()
                .addGap(354, 354, 354)
                .addGroup(CustomizeRoutineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(CustomizeRoutineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(Busy, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(348, Short.MAX_VALUE))
        );

        Menu.addTab("tab1", CustomizeRoutine);

        AddSession.setBackground(new java.awt.Color(255, 102, 0));

        SessionName.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        SessionAdd.setBackground(new java.awt.Color(0, 153, 0));
        SessionAdd.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        SessionAdd.setForeground(new java.awt.Color(255, 255, 255));
        SessionAdd.setText("ADD");
        SessionAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SessionAddMouseClicked(evt);
            }
        });
        SessionAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SessionAddActionPerformed(evt);
            }
        });

        jLabel44.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(0, 102, 0));
        jLabel44.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel44.setText("Add Session");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 102, 0));
        jLabel8.setText("Session Name");

        Add1.setBackground(new java.awt.Color(255, 255, 0));
        Add1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Add1.setForeground(new java.awt.Color(255, 255, 255));
        Add1.setText("Update");
        Add1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Add1ActionPerformed(evt);
            }
        });

        SessionTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Session Name"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        SessionTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SessionTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(SessionTable);

        Add2.setBackground(new java.awt.Color(255, 0, 0));
        Add2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Add2.setForeground(new java.awt.Color(255, 255, 255));
        Add2.setText("Delete");
        Add2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Add2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout AddSessionLayout = new javax.swing.GroupLayout(AddSession);
        AddSession.setLayout(AddSessionLayout);
        AddSessionLayout.setHorizontalGroup(
            AddSessionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddSessionLayout.createSequentialGroup()
                .addGroup(AddSessionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AddSessionLayout.createSequentialGroup()
                        .addGap(269, 269, 269)
                        .addGroup(AddSessionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(AddSessionLayout.createSequentialGroup()
                                .addGroup(AddSessionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(AddSessionLayout.createSequentialGroup()
                                        .addComponent(SessionName, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(SessionAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addComponent(Add1, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(AddSessionLayout.createSequentialGroup()
                                .addGap(176, 176, 176)
                                .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(AddSessionLayout.createSequentialGroup()
                        .addGap(505, 505, 505)
                        .addComponent(Add2, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(278, Short.MAX_VALUE))
        );
        AddSessionLayout.setVerticalGroup(
            AddSessionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddSessionLayout.createSequentialGroup()
                .addGap(192, 192, 192)
                .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(AddSessionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SessionName, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SessionAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Add1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Add2, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(232, Short.MAX_VALUE))
        );

        Menu.addTab("tab1", AddSession);

        AddProfessor.setBackground(new java.awt.Color(255, 102, 0));

        professorNumber.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        professorNumber.setForeground(new java.awt.Color(0, 102, 0));
        professorNumber.setText("Professor Number");

        jLabel46.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(0, 102, 0));
        jLabel46.setText("Professor Name");

        professorNumber2.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        professorNumber2.setForeground(new java.awt.Color(0, 102, 0));
        professorNumber2.setText("Professor Code");

        DeleteProfessor.setBackground(new java.awt.Color(255, 0, 0));
        DeleteProfessor.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        DeleteProfessor.setForeground(new java.awt.Color(255, 255, 255));
        DeleteProfessor.setText("DELETE");
        DeleteProfessor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteProfessorActionPerformed(evt);
            }
        });

        ProfessorAdd.setBackground(new java.awt.Color(0, 102, 0));
        ProfessorAdd.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        ProfessorAdd.setForeground(new java.awt.Color(255, 255, 255));
        ProfessorAdd.setText("ADD");
        ProfessorAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ProfessorAddActionPerformed(evt);
            }
        });

        ProfessorUpdate.setBackground(new java.awt.Color(255, 204, 0));
        ProfessorUpdate.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        ProfessorUpdate.setForeground(new java.awt.Color(255, 255, 255));
        ProfessorUpdate.setText("UPDATE");
        ProfessorUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ProfessorUpdateActionPerformed(evt);
            }
        });

        jLabel45.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(0, 102, 0));
        jLabel45.setText("Add Professor");

        ProfessorTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Professor Name", "Job Year", "Code", "Phone Number"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        ProfessorTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ProfessorTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(ProfessorTable);

        jLabel48.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(0, 102, 0));
        jLabel48.setText("Job Year");

        javax.swing.GroupLayout AddProfessorLayout = new javax.swing.GroupLayout(AddProfessor);
        AddProfessor.setLayout(AddProfessorLayout);
        AddProfessorLayout.setHorizontalGroup(
            AddProfessorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddProfessorLayout.createSequentialGroup()
                .addGroup(AddProfessorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AddProfessorLayout.createSequentialGroup()
                        .addGap(427, 427, 427)
                        .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(AddProfessorLayout.createSequentialGroup()
                        .addGap(106, 106, 106)
                        .addGroup(AddProfessorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(AddProfessorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(AddProfessorLayout.createSequentialGroup()
                                    .addGroup(AddProfessorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(ProfessorName, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(AddProfessorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(professorNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(ProfessorNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(18, 18, 18)
                                    .addGroup(AddProfessorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(professorNumber2, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(ProfessorCode, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 876, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(AddProfessorLayout.createSequentialGroup()
                                    .addComponent(JobYear, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(ProfessorAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGap(18, 18, 18)
                                    .addComponent(ProfessorUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(AddProfessorLayout.createSequentialGroup()
                        .addGap(497, 497, 497)
                        .addComponent(DeleteProfessor, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(148, Short.MAX_VALUE))
        );
        AddProfessorLayout.setVerticalGroup(
            AddProfessorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddProfessorLayout.createSequentialGroup()
                .addGap(69, 69, 69)
                .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(AddProfessorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(AddProfessorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(AddProfessorLayout.createSequentialGroup()
                            .addComponent(jLabel46)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(ProfessorName, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AddProfessorLayout.createSequentialGroup()
                            .addComponent(professorNumber2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(ProfessorCode, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(AddProfessorLayout.createSequentialGroup()
                        .addComponent(professorNumber)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ProfessorNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel48)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(AddProfessorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JobYear, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ProfessorAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ProfessorUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(DeleteProfessor, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(221, Short.MAX_VALUE))
        );

        Menu.addTab("tab1", AddProfessor);

        AddCourse.setBackground(new java.awt.Color(255, 102, 0));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 102, 0));
        jLabel9.setText("Add Course");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 102, 0));
        jLabel10.setText("Course Name");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 102, 0));
        jLabel11.setText("Course Code");

        SessionComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SessionComboBoxActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 102, 0));
        jLabel13.setText("Sessions");

        Add_Course.setBackground(new java.awt.Color(0, 102, 0));
        Add_Course.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        Add_Course.setForeground(new java.awt.Color(255, 255, 255));
        Add_Course.setText("ADD");
        Add_Course.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Add_CourseActionPerformed(evt);
            }
        });

        UpdateCourse.setBackground(new java.awt.Color(255, 255, 0));
        UpdateCourse.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        UpdateCourse.setForeground(new java.awt.Color(255, 255, 255));
        UpdateCourse.setText("Update");
        UpdateCourse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateCourseActionPerformed(evt);
            }
        });

        CourseTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Course Name", "Course Code", "Slots", "Per Week Class", "Session"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        CourseTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CourseTableMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(CourseTable);

        DeleteCourse.setBackground(new java.awt.Color(204, 0, 0));
        DeleteCourse.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        DeleteCourse.setForeground(new java.awt.Color(255, 255, 255));
        DeleteCourse.setText("Delete");
        DeleteCourse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteCourseActionPerformed(evt);
            }
        });

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(0, 102, 0));
        jLabel24.setText("Slots");

        WeekComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                WeekComboBoxActionPerformed(evt);
            }
        });

        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(0, 102, 0));
        jLabel27.setText("Per Week Class");

        javax.swing.GroupLayout AddCourseLayout = new javax.swing.GroupLayout(AddCourse);
        AddCourse.setLayout(AddCourseLayout);
        AddCourseLayout.setHorizontalGroup(
            AddCourseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddCourseLayout.createSequentialGroup()
                .addGroup(AddCourseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AddCourseLayout.createSequentialGroup()
                        .addGap(299, 299, 299)
                        .addGroup(AddCourseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(AddCourseLayout.createSequentialGroup()
                                .addGap(145, 145, 145)
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(AddCourseLayout.createSequentialGroup()
                                .addGroup(AddCourseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(CourseName, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(AddCourseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(CourseCode, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 494, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(AddCourseLayout.createSequentialGroup()
                                .addGroup(AddCourseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(SlotComboBox, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel24, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(AddCourseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
                                    .addComponent(SessionComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(AddCourseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(WeekComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(AddCourseLayout.createSequentialGroup()
                        .addGap(405, 405, 405)
                        .addComponent(UpdateCourse, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)
                        .addComponent(Add_Course, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(AddCourseLayout.createSequentialGroup()
                        .addGap(496, 496, 496)
                        .addComponent(DeleteCourse, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(610, Short.MAX_VALUE))
        );
        AddCourseLayout.setVerticalGroup(
            AddCourseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddCourseLayout.createSequentialGroup()
                .addGap(115, 115, 115)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58)
                .addGroup(AddCourseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(AddCourseLayout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(CourseName, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(AddCourseLayout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(CourseCode, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(AddCourseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AddCourseLayout.createSequentialGroup()
                        .addGroup(AddCourseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel24)
                            .addComponent(jLabel13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(AddCourseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(SlotComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(SessionComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(AddCourseLayout.createSequentialGroup()
                        .addComponent(jLabel27)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(WeekComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(29, 29, 29)
                .addGroup(AddCourseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(UpdateCourse, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Add_Course, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(DeleteCourse, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(247, Short.MAX_VALUE))
        );

        Menu.addTab("tab1", AddCourse);

        AssignRoutine.setBackground(new java.awt.Color(255, 102, 0));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 102, 0));
        jLabel14.setText("Assign Courses");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 102, 0));
        jLabel15.setText("Session");

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 102, 0));
        jLabel16.setText("Course");

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 102, 0));
        jLabel17.setText("Teacher");

        SearchCourse.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        SearchCourse.setForeground(new java.awt.Color(255, 255, 255));
        SearchCourse.setIcon(new javax.swing.ImageIcon(getClass().getResource("/smart.png"))); // NOI18N
        SearchCourse.setText("Search");
        SearchCourse.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SearchCourseMouseClicked(evt);
            }
        });

        jButton7.setBackground(new java.awt.Color(0, 102, 0));
        jButton7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setText("Assign");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        AssignTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Session", "Course", "Teacher"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(AssignTable);

        jButton8.setBackground(new java.awt.Color(204, 0, 0));
        jButton8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton8.setForeground(new java.awt.Color(255, 255, 255));
        jButton8.setText("Delete");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout AssignRoutineLayout = new javax.swing.GroupLayout(AssignRoutine);
        AssignRoutine.setLayout(AssignRoutineLayout);
        AssignRoutineLayout.setHorizontalGroup(
            AssignRoutineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AssignRoutineLayout.createSequentialGroup()
                .addGroup(AssignRoutineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AssignRoutineLayout.createSequentialGroup()
                        .addGap(411, 411, 411)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(207, 207, 207))
                    .addGroup(AssignRoutineLayout.createSequentialGroup()
                        .addGap(238, 238, 238)
                        .addGroup(AssignRoutineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 647, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(AssignRoutineLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(AssignRoutineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(AssignRoutineLayout.createSequentialGroup()
                                        .addGroup(AssignRoutineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(AssignRoutineLayout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(SearchCourse))
                                            .addGroup(AssignRoutineLayout.createSequentialGroup()
                                                .addGroup(AssignRoutineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(AssignSessionComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(AssignRoutineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(AssignCourseComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                        .addGap(56, 56, 56)
                                        .addGroup(AssignRoutineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(AssignProfessorComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))))))))
                .addGap(245, 245, 245))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AssignRoutineLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(498, 498, 498))
        );
        AssignRoutineLayout.setVerticalGroup(
            AssignRoutineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AssignRoutineLayout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51)
                .addGroup(AssignRoutineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AssignRoutineLayout.createSequentialGroup()
                        .addGroup(AssignRoutineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(jLabel17))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(AssignRoutineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(AssignSessionComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(AssignProfessorComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(AssignRoutineLayout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(AssignCourseComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SearchCourse, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(278, Short.MAX_VALUE))
        );

        Menu.addTab("tab1", AssignRoutine);

        Slot.setBackground(new java.awt.Color(255, 102, 0));

        SlotName.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        Add3.setBackground(new java.awt.Color(0, 153, 0));
        Add3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Add3.setForeground(new java.awt.Color(255, 255, 255));
        Add3.setText("ADD");
        Add3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Add3ActionPerformed(evt);
            }
        });

        Add4.setBackground(new java.awt.Color(255, 255, 0));
        Add4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Add4.setForeground(new java.awt.Color(255, 255, 255));
        Add4.setText("Update");
        Add4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Add4ActionPerformed(evt);
            }
        });

        jLabel19.setBackground(new java.awt.Color(0, 102, 0));
        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 102, 0));
        jLabel19.setText("Slots Name");

        SlotTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Slot Name"
            }
        ));
        SlotTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SlotTableMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(SlotTable);

        Add5.setBackground(new java.awt.Color(255, 0, 0));
        Add5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Add5.setForeground(new java.awt.Color(255, 255, 255));
        Add5.setText("Delete");
        Add5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Add5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout SlotLayout = new javax.swing.GroupLayout(Slot);
        Slot.setLayout(SlotLayout);
        SlotLayout.setHorizontalGroup(
            SlotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SlotLayout.createSequentialGroup()
                .addGap(268, 268, 268)
                .addGroup(SlotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(SlotLayout.createSequentialGroup()
                        .addComponent(SlotName, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(Add3, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(Add4, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(552, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SlotLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(SlotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SlotLayout.createSequentialGroup()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(337, 337, 337))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SlotLayout.createSequentialGroup()
                        .addComponent(Add5, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(498, 498, 498))))
        );
        SlotLayout.setVerticalGroup(
            SlotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SlotLayout.createSequentialGroup()
                .addGap(279, 279, 279)
                .addComponent(jLabel19)
                .addGap(18, 18, 18)
                .addGroup(SlotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SlotName, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Add3, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Add4, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Add5, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(222, Short.MAX_VALUE))
        );

        Menu.addTab("tab1", Slot);

        Day.setBackground(new java.awt.Color(255, 102, 0));

        DayName.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        AddDay.setBackground(new java.awt.Color(0, 153, 0));
        AddDay.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        AddDay.setForeground(new java.awt.Color(255, 255, 255));
        AddDay.setText("ADD");
        AddDay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddDayActionPerformed(evt);
            }
        });

        UpdateDay.setBackground(new java.awt.Color(255, 255, 0));
        UpdateDay.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        UpdateDay.setForeground(new java.awt.Color(255, 255, 255));
        UpdateDay.setText("Update");
        UpdateDay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateDayActionPerformed(evt);
            }
        });

        jLabel20.setBackground(new java.awt.Color(0, 102, 0));
        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(0, 102, 0));
        jLabel20.setText("Days Name");

        DayTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Days Name"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        DayTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                DayTableMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(DayTable);

        DeleteDay.setBackground(new java.awt.Color(255, 0, 0));
        DeleteDay.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        DeleteDay.setForeground(new java.awt.Color(255, 255, 255));
        DeleteDay.setText("Delete");
        DeleteDay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteDayActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout DayLayout = new javax.swing.GroupLayout(Day);
        Day.setLayout(DayLayout);
        DayLayout.setHorizontalGroup(
            DayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DayLayout.createSequentialGroup()
                .addGap(268, 268, 268)
                .addGroup(DayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(DayLayout.createSequentialGroup()
                        .addComponent(DayName, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(AddDay, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(UpdateDay, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(552, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DayLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(DayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DayLayout.createSequentialGroup()
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(337, 337, 337))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DayLayout.createSequentialGroup()
                        .addComponent(DeleteDay, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(498, 498, 498))))
        );
        DayLayout.setVerticalGroup(
            DayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DayLayout.createSequentialGroup()
                .addGap(279, 279, 279)
                .addComponent(jLabel20)
                .addGap(18, 18, 18)
                .addGroup(DayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(DayName, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AddDay, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(UpdateDay, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(DeleteDay, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(222, Short.MAX_VALUE))
        );

        Menu.addTab("tab1", Day);

        Room.setBackground(new java.awt.Color(255, 102, 0));

        RoomName.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        AddRoom.setBackground(new java.awt.Color(0, 153, 0));
        AddRoom.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        AddRoom.setForeground(new java.awt.Color(255, 255, 255));
        AddRoom.setText("ADD");
        AddRoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddRoomActionPerformed(evt);
            }
        });

        jLabel21.setBackground(new java.awt.Color(0, 102, 0));
        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(0, 102, 0));
        jLabel21.setText("Room Number");

        RoomTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Room Number", "Category"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane7.setViewportView(RoomTable);

        DeleteRoom.setBackground(new java.awt.Color(255, 0, 0));
        DeleteRoom.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        DeleteRoom.setForeground(new java.awt.Color(255, 255, 255));
        DeleteRoom.setText("Delete");
        DeleteRoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteRoomActionPerformed(evt);
            }
        });

        RoomCategoryComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Class Room", "Lab Room" }));

        javax.swing.GroupLayout RoomLayout = new javax.swing.GroupLayout(Room);
        Room.setLayout(RoomLayout);
        RoomLayout.setHorizontalGroup(
            RoomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RoomLayout.createSequentialGroup()
                .addGroup(RoomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(RoomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, RoomLayout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(RoomName, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(RoomCategoryComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(AddRoom, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(RoomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(RoomLayout.createSequentialGroup()
                            .addGap(530, 530, 530)
                            .addComponent(DeleteRoom, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(RoomLayout.createSequentialGroup()
                            .addGap(350, 350, 350)
                            .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(328, Short.MAX_VALUE))
        );
        RoomLayout.setVerticalGroup(
            RoomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RoomLayout.createSequentialGroup()
                .addGap(279, 279, 279)
                .addComponent(jLabel21)
                .addGap(18, 18, 18)
                .addGroup(RoomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(RoomCategoryComboBox, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                    .addGroup(RoomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(AddRoom, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(RoomName, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(30, 30, 30)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(DeleteRoom, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(222, 222, 222))
        );

        Menu.addTab("tab1", Room);

        CourseRoom.setBackground(new java.awt.Color(255, 102, 0));

        jLabel22.setBackground(new java.awt.Color(0, 102, 0));
        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(0, 102, 0));
        jLabel22.setText("Room Number");

        jLabel23.setBackground(new java.awt.Color(0, 102, 0));
        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(0, 102, 0));
        jLabel23.setText("Course Name");

        CourseRoomTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Course Name", "Room Number"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane9.setViewportView(CourseRoomTable);

        AddCourseRoom.setBackground(new java.awt.Color(0, 153, 0));
        AddCourseRoom.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        AddCourseRoom.setForeground(new java.awt.Color(255, 255, 255));
        AddCourseRoom.setText("ADD");
        AddCourseRoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddCourseRoomActionPerformed(evt);
            }
        });

        DeleteCourseRoom.setBackground(new java.awt.Color(204, 0, 0));
        DeleteCourseRoom.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        DeleteCourseRoom.setForeground(new java.awt.Color(255, 255, 255));
        DeleteCourseRoom.setText("Delete");
        DeleteCourseRoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteCourseRoomActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout CourseRoomLayout = new javax.swing.GroupLayout(CourseRoom);
        CourseRoom.setLayout(CourseRoomLayout);
        CourseRoomLayout.setHorizontalGroup(
            CourseRoomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CourseRoomLayout.createSequentialGroup()
                .addGroup(CourseRoomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(CourseRoomLayout.createSequentialGroup()
                        .addGap(254, 254, 254)
                        .addGroup(CourseRoomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CourseComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(38, 38, 38)
                        .addGroup(CourseRoomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(RoomComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(CourseRoomLayout.createSequentialGroup()
                        .addGap(393, 393, 393)
                        .addComponent(AddCourseRoom, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(CourseRoomLayout.createSequentialGroup()
                        .addGap(230, 230, 230)
                        .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(CourseRoomLayout.createSequentialGroup()
                        .addGap(405, 405, 405)
                        .addComponent(DeleteCourseRoom, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(721, Short.MAX_VALUE))
        );
        CourseRoomLayout.setVerticalGroup(
            CourseRoomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CourseRoomLayout.createSequentialGroup()
                .addGap(220, 220, 220)
                .addGroup(CourseRoomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(CourseRoomLayout.createSequentialGroup()
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(CourseComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(CourseRoomLayout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(RoomComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(AddCourseRoom, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(DeleteCourseRoom, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(248, Short.MAX_VALUE))
        );

        Menu.addTab("tab1", CourseRoom);

        SetSignature.setBackground(new java.awt.Color(255, 102, 0));

        SignName.setColumns(20);
        SignName.setRows(5);
        jScrollPane11.setViewportView(SignName);

        Addsign.setBackground(new java.awt.Color(0, 102, 0));
        Addsign.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        Addsign.setForeground(new java.awt.Color(255, 255, 255));
        Addsign.setText("Set");
        Addsign.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddsignActionPerformed(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(0, 102, 0));
        jLabel25.setText("Signature");

        javax.swing.GroupLayout SetSignatureLayout = new javax.swing.GroupLayout(SetSignature);
        SetSignature.setLayout(SetSignatureLayout);
        SetSignatureLayout.setHorizontalGroup(
            SetSignatureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SetSignatureLayout.createSequentialGroup()
                .addGap(275, 275, 275)
                .addGroup(SetSignatureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 565, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(SetSignatureLayout.createSequentialGroup()
                        .addGap(225, 225, 225)
                        .addComponent(Addsign, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(SetSignatureLayout.createSequentialGroup()
                        .addGap(208, 208, 208)
                        .addComponent(jLabel25)))
                .addContainerGap(563, Short.MAX_VALUE))
        );
        SetSignatureLayout.setVerticalGroup(
            SetSignatureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SetSignatureLayout.createSequentialGroup()
                .addGap(235, 235, 235)
                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(Addsign, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(310, Short.MAX_VALUE))
        );

        Menu.addTab("tab1", SetSignature);

        SetTitle.setBackground(new java.awt.Color(255, 102, 0));

        TitleName.setColumns(20);
        TitleName.setRows(5);
        jScrollPane12.setViewportView(TitleName);

        Addtitle.setBackground(new java.awt.Color(0, 102, 0));
        Addtitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        Addtitle.setForeground(new java.awt.Color(255, 255, 255));
        Addtitle.setText("Set");
        Addtitle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddtitleActionPerformed(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(0, 102, 0));
        jLabel26.setText("Title");
        jLabel26.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout SetTitleLayout = new javax.swing.GroupLayout(SetTitle);
        SetTitle.setLayout(SetTitleLayout);
        SetTitleLayout.setHorizontalGroup(
            SetTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SetTitleLayout.createSequentialGroup()
                .addGroup(SetTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SetTitleLayout.createSequentialGroup()
                        .addGap(215, 215, 215)
                        .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 565, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(SetTitleLayout.createSequentialGroup()
                        .addGap(440, 440, 440)
                        .addComponent(Addtitle, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(SetTitleLayout.createSequentialGroup()
                        .addGap(474, 474, 474)
                        .addComponent(jLabel26)))
                .addContainerGap(623, Short.MAX_VALUE))
        );
        SetTitleLayout.setVerticalGroup(
            SetTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SetTitleLayout.createSequentialGroup()
                .addGap(152, 152, 152)
                .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(Addtitle, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(393, Short.MAX_VALUE))
        );

        Menu.addTab("tab1", SetTitle);

        AssignBusySchedule.setBackground(new java.awt.Color(255, 102, 0));

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 102, 0));
        jLabel18.setText("Busy Schedule");

        CategoryComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Professor", "Session" }));
        CategoryComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                CategoryComboBoxItemStateChanged(evt);
            }
        });

        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(0, 102, 0));
        jLabel28.setText("Option");

        label001.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        label001.setForeground(new java.awt.Color(0, 102, 0));
        label001.setText("Selected List");

        jLabel30.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(0, 102, 0));
        jLabel30.setText("Day");

        jButton15.setBackground(new java.awt.Color(0, 102, 0));
        jButton15.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton15.setForeground(new java.awt.Color(255, 255, 255));
        jButton15.setText("Busy");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        jButton16.setBackground(new java.awt.Color(204, 0, 0));
        jButton16.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton16.setForeground(new java.awt.Color(255, 255, 255));
        jButton16.setText("Reset");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        BusySlotComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BusySlotComboBoxActionPerformed(evt);
            }
        });

        jLabel31.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(0, 102, 0));
        jLabel31.setText("Slot");

        javax.swing.GroupLayout AssignBusyScheduleLayout = new javax.swing.GroupLayout(AssignBusySchedule);
        AssignBusySchedule.setLayout(AssignBusyScheduleLayout);
        AssignBusyScheduleLayout.setHorizontalGroup(
            AssignBusyScheduleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AssignBusyScheduleLayout.createSequentialGroup()
                .addGap(245, 245, 245)
                .addGroup(AssignBusyScheduleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AssignBusyScheduleLayout.createSequentialGroup()
                        .addGroup(AssignBusyScheduleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(AssignBusyScheduleLayout.createSequentialGroup()
                                .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AssignBusyScheduleLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(CategoryComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(12, 12, 12)
                        .addGroup(AssignBusyScheduleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(SelectComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label001, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(AssignBusyScheduleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BusyDayComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(AssignBusyScheduleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BusySlotComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(598, 598, 598))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AssignBusyScheduleLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(758, 758, 758))))
            .addGroup(AssignBusyScheduleLayout.createSequentialGroup()
                .addGap(401, 401, 401)
                .addComponent(jLabel18)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        AssignBusyScheduleLayout.setVerticalGroup(
            AssignBusyScheduleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AssignBusyScheduleLayout.createSequentialGroup()
                .addGap(218, 218, 218)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(AssignBusyScheduleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label001)
                    .addComponent(jLabel30)
                    .addComponent(jLabel31)
                    .addComponent(jLabel28))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(AssignBusyScheduleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SelectComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BusyDayComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BusySlotComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CategoryComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(AssignBusyScheduleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(527, Short.MAX_VALUE))
        );

        Menu.addTab("tab1", AssignBusySchedule);

        Generate.setBackground(new java.awt.Color(255, 102, 0));

        jButton17.setBackground(new java.awt.Color(0, 102, 0));
        jButton17.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jButton17.setForeground(new java.awt.Color(255, 255, 255));
        jButton17.setText("Professor Routine");
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });

        jButton18.setBackground(new java.awt.Color(0, 102, 0));
        jButton18.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jButton18.setForeground(new java.awt.Color(255, 255, 255));
        jButton18.setText("Combine Routine");
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });

        jButton19.setBackground(new java.awt.Color(0, 102, 0));
        jButton19.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jButton19.setForeground(new java.awt.Color(255, 255, 255));
        jButton19.setText("Session Routine");
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });

        jButton21.setBackground(new java.awt.Color(0, 102, 0));
        jButton21.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jButton21.setForeground(new java.awt.Color(255, 255, 255));
        jButton21.setText("Professor's Course");
        jButton21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton21ActionPerformed(evt);
            }
        });

        jButton22.setBackground(new java.awt.Color(0, 102, 0));
        jButton22.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jButton22.setForeground(new java.awt.Color(255, 255, 255));
        jButton22.setText("Session's Courses");
        jButton22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton22ActionPerformed(evt);
            }
        });

        Busy1.setBackground(new java.awt.Color(0, 102, 0));
        Busy1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        Busy1.setForeground(new java.awt.Color(255, 255, 255));
        Busy1.setText("Generate Routine");
        Busy1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Busy1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout GenerateLayout = new javax.swing.GroupLayout(Generate);
        Generate.setLayout(GenerateLayout);
        GenerateLayout.setHorizontalGroup(
            GenerateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GenerateLayout.createSequentialGroup()
                .addGap(212, 212, 212)
                .addGroup(GenerateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, GenerateLayout.createSequentialGroup()
                        .addComponent(jButton22, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(GenerateLayout.createSequentialGroup()
                        .addComponent(jButton18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton17))
                    .addComponent(Busy1, javax.swing.GroupLayout.PREFERRED_SIZE, 677, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(514, Short.MAX_VALUE))
        );
        GenerateLayout.setVerticalGroup(
            GenerateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GenerateLayout.createSequentialGroup()
                .addGap(341, 341, 341)
                .addComponent(Busy1, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(GenerateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton18, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton19, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(GenerateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton21, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton22, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(389, Short.MAX_VALUE))
        );

        Menu.addTab("tab1", Generate);

        jPanel3.add(Menu, new org.netbeans.lib.awtextra.AbsoluteConstraints(517, -40, 1130, 990));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SaveAndExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SaveAndExitMouseClicked
        // TODO add your handling code here:
        // Session Table
        DefaultTableModel table = (DefaultTableModel)SessionTable.getModel();
        if(table.getRowCount() == 0){
            DatabaseConnection cn = new DatabaseConnection();
            Connection connection = cn.getConnection();
            String query = "delete from session";
            try{
                Statement statement = connection.createStatement();
                int deleted = statement.executeUpdate(query);
                System.out.println("Full Deleted Session Table");
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        else{
            DatabaseConnection cn = new DatabaseConnection();
            Connection connection = cn.getConnection();
            String query = "delete from session";
            try{
                Statement statement = connection.createStatement();
                int deleted = statement.executeUpdate(query);
                System.out.print(deleted);
                for(int i=0;i<table.getRowCount();i++){
                    String session = table.getValueAt(i,0).toString();
                    query  = "insert into session(Name) values(?)";
                    PreparedStatement preparedstatement = connection.prepareStatement(query);
                    preparedstatement.setString(1,session);
                    preparedstatement.execute();
                }
                System.out.println("Session Table Saved!");
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        //Slot Table
        table = (DefaultTableModel)SlotTable.getModel();
        if(table.getRowCount() == 0){
            DatabaseConnection cn = new DatabaseConnection();
            Connection connection = cn.getConnection();
            String query = "delete from slot";
            try{
                Statement statement = connection.createStatement();
                int deleted = statement.executeUpdate(query);
                System.out.println("Slot Table Deleted!");
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        else{
            DatabaseConnection cn = new DatabaseConnection();
            Connection connection = cn.getConnection();
            String query = "delete from slot";
            try{
                Statement statement = connection.createStatement();
                int deleted = statement.executeUpdate(query);
                System.out.print(deleted);
                for(int i=0;i<table.getRowCount();i++){
                    String slot = table.getValueAt(i,0).toString();
                    query  = "insert into slot(slot_name) values(?)";
                    PreparedStatement preparedstatement = connection.prepareStatement(query);
                    preparedstatement.setString(1,slot);
                    preparedstatement.execute();
                }
                System.out.println("Slot Table Saved!");
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        //Day Table
        table = (DefaultTableModel)DayTable.getModel();
        if(table.getRowCount() == 0){
            DatabaseConnection cn = new DatabaseConnection();
            Connection connection = cn.getConnection();
            String query = "delete from day";
            try{
                Statement statement = connection.createStatement();
                int deleted = statement.executeUpdate(query);
                System.out.println("Day Table Deleted!");
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        else{
            DatabaseConnection cn = new DatabaseConnection();
            Connection connection = cn.getConnection();
            String query = "delete from day";
            try{
                Statement statement = connection.createStatement();
                int deleted = statement.executeUpdate(query);
                System.out.print(deleted);
                for(int i=0;i<table.getRowCount();i++){
                    String day = table.getValueAt(i,0).toString();
                    query  = "insert into day(day_name) values(?)";
                    PreparedStatement preparedstatement = connection.prepareStatement(query);
                    preparedstatement.setString(1,day);
                    preparedstatement.execute();
                    System.out.println("Day Table Saved!");
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        
        //Room Table
        table = (DefaultTableModel)RoomTable.getModel();
        if(table.getRowCount() == 0){
            DatabaseConnection cn = new DatabaseConnection();
            Connection connection = cn.getConnection();
            String query = "delete from room";
            try{
                Statement statement = connection.createStatement();
                int deleted = statement.executeUpdate(query);
                System.out.println("Room Table Deleted!");
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        else{
            DatabaseConnection cn = new DatabaseConnection();
            Connection connection = cn.getConnection();
            String query = "delete from room";
            try{
                Statement statement = connection.createStatement();
                int deleted = statement.executeUpdate(query);
                System.out.print(deleted);
                for(int i=0;i<table.getRowCount();i++){
                    String room = table.getValueAt(i,0).toString();
                    String category = table.getValueAt(i,1).toString();
                    query  = "insert into room(room_name,Category) values(?,?)";
                    PreparedStatement preparedstatement = connection.prepareStatement(query);
                    preparedstatement.setString(1,room);
                    preparedstatement.setString(2,category);
                    preparedstatement.execute();
                }
                System.out.println("Room Table Saved!");
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        //CourseRoom Table
        table = (DefaultTableModel)CourseRoomTable.getModel();
        if(table.getRowCount() == 0){
            DatabaseConnection cn = new DatabaseConnection();
            Connection connection = cn.getConnection();
            String query = "delete from courseroom";
            try{
                Statement statement = connection.createStatement();
                int deleted = statement.executeUpdate(query);
                System.out.println("CourseRoom Table Deleted!");
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        else{
            DatabaseConnection cn = new DatabaseConnection();
            Connection connection = cn.getConnection();
            String query = "delete from courseroom";
            try{
                Statement statement = connection.createStatement();
                int deleted = statement.executeUpdate(query);
                System.out.print(deleted);
                for(int i=0;i<table.getRowCount();i++){
                    String code = table.getValueAt(i,0).toString();
                    String room = table.getValueAt(i,1).toString();
                    query  = "insert into courseroom(course_code,course_room) values(?,?)";
                    PreparedStatement preparedstatement = connection.prepareStatement(query);
                    preparedstatement.setString(1,code);
                    preparedstatement.setString(2,room);
                    preparedstatement.execute();
                }
                System.out.println("Course Table Saved!");
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        //
        //Professor Table
        table = (DefaultTableModel)ProfessorTable.getModel();
        if(table.getRowCount() == 0){
            DatabaseConnection cn = new DatabaseConnection();
            Connection connection = cn.getConnection();
            String query = "delete from professor";
            try{
                Statement statement = connection.createStatement();
                int deleted = statement.executeUpdate(query);
                System.out.println("Professor Table Deleted!");
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        else{
            DatabaseConnection cn = new DatabaseConnection();
            Connection connection = cn.getConnection();
            String query = "delete from professor";
            try{
                Statement statement = connection.createStatement();
                int deleted = statement.executeUpdate(query);
                System.out.print(deleted);
                for(int i=0;i<table.getRowCount();i++){
                    String name = table.getValueAt(i,0).toString();
                    String jobyear = table.getValueAt(i,1).toString();
                    String code = table.getValueAt(i,2).toString();
                    String number = table.getValueAt(i,3).toString();
                    query  = "insert into professor(professor_name,professor_code,professor_number,professor_jobyear) values(?,?,?,?)";
                    PreparedStatement preparedstatement = connection.prepareStatement(query);
                    preparedstatement.setString(1,name);
                    preparedstatement.setString(2,code);
                    preparedstatement.setString(3,number);
                    preparedstatement.setString(4,jobyear);
                    preparedstatement.execute();
                }
                System.out.println("Professor Table Saved!");
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        //Course Table
        table = (DefaultTableModel)CourseTable.getModel();
        if(table.getRowCount() == 0){
            DatabaseConnection cn = new DatabaseConnection();
            Connection connection = cn.getConnection();
            String query = "delete from course";
            try{
                Statement statement = connection.createStatement();
                int deleted = statement.executeUpdate(query);
                System.out.println("Course Table Deleted!");
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        else{
            DatabaseConnection cn = new DatabaseConnection();
            Connection connection = cn.getConnection();
            String query = "delete from course";
            try{
                Statement statement = connection.createStatement();
                int deleted = statement.executeUpdate(query);
                System.out.print(deleted);
                for(int i=0;i<table.getRowCount();i++){
                    String name = table.getValueAt(i,0).toString();
                    String code = table.getValueAt(i,1).toString();
                    String slot = table.getValueAt(i,2).toString();
                    String perweekclass = table.getValueAt(i,3).toString();
                    String session = table.getValueAt(i,4).toString();
                    query  = "insert into course(course_name,course_code,course_slot,course_perweekclass,course_session) values(?,?,?,?,?)";
                    PreparedStatement preparedstatement = connection.prepareStatement(query);
                    preparedstatement.setString(1,name);
                    preparedstatement.setString(2,code);
                    preparedstatement.setString(3,slot);
                    preparedstatement.setString(4,perweekclass);
                    preparedstatement.setString(5,session);
                    preparedstatement.execute();
                }
                System.out.println("Course Table Saved!");
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        JOptionPane.showMessageDialog(this,"Saved Successfully!");
        dispose();
    }//GEN-LAST:event_SaveAndExitMouseClicked

    private void jLabel5MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MousePressed

    }//GEN-LAST:event_jLabel5MousePressed

    private void jLabel5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseEntered
        // TODO add your handling code here:
        jLabel5.setForeground(Color.YELLOW);
    }//GEN-LAST:event_jLabel5MouseEntered

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        // TODO add your handling code here:
        Menu.setSelectedIndex(2);
        DefaultTableModel table = (DefaultTableModel)SessionTable.getModel();
        table.getDataVector().removeAllElements();
        for(String s : SessionList){
            String[] Ses = {s};
            table.addRow(Ses);
        }
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        SessionTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        SessionTable.getTableHeader().setDefaultRenderer(centerRenderer);
    }//GEN-LAST:event_jLabel4MouseClicked

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        // TODO add your handling code here:
        Menu.setSelectedIndex(4);
        SessionComboBox.removeAllItems();
        SlotComboBox.removeAllItems();
        WeekComboBox.removeAllItems();
        SessionComboBox.addItem("");
        SlotComboBox.addItem("");
        WeekComboBox.addItem("");
        for(String s : SessionList){
            SessionComboBox.addItem(s);
        }
        for(int i=1;i<=SlotList.size();i++){
            SlotComboBox.addItem(String.valueOf(i));
        }
        for(int i=1;i<=DayList.size();i++){
            WeekComboBox.addItem(String.valueOf(i));
        }
        DefaultTableModel table = (DefaultTableModel)CourseTable.getModel();
        table.getDataVector().removeAllElements();
        for(String s : CourseList){
            Course x = CR.get(s);
            String c_name = x.Name;
            String c_code = x.Code;
            String slot_count =  x.Slots;
            String session_name = x.Session;
            String perweekclass = x.PerWeek;
            String Data[] = {c_name,c_code,slot_count,perweekclass,session_name};
            table.addRow(Data);
        }
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for(int i=0;i<5;i++){
            CourseTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        CourseTable.getTableHeader().setDefaultRenderer(centerRenderer);
    }//GEN-LAST:event_jLabel2MouseClicked

    private void DeleteCourseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteCourseActionPerformed
        // TODO add your handling code here:
        int row = CourseTable.getSelectedRow();
        if(row == -1){
            JOptionPane.showMessageDialog(this, "Please First Select The Row !");
        }
        else{
            DefaultTableModel table = (DefaultTableModel)CourseTable.getModel();
            String c_code = table.getValueAt(row, 1).toString();
            String session_name = table.getValueAt(row, 4).toString();
            CR.remove(c_code);
            CourseList.remove(c_code);
            for(String s : SessionList){
                if(s == session_name){
                    Session ss = S.get(session_name);
                    ss.Courses.remove(c_code);
                }
            }
            CourseName.setText("");
            CourseCode.setText("");
            SlotComboBox.getModel().setSelectedItem("");
            SessionComboBox.getModel().setSelectedItem("");
            WeekComboBox.getModel().setSelectedItem("");
            table.removeRow(row);
            JOptionPane.showMessageDialog(this, "Succesfully Deleted !");
            System.out.println(CourseList);
            System.out.println(CR);
        }
    }//GEN-LAST:event_DeleteCourseActionPerformed

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
        // TODO add your handling code here:
        Menu.setSelectedIndex(5);
        AssignSessionComboBox.removeAllItems();
        AssignCourseComboBox.removeAllItems();
        AssignProfessorComboBox.removeAllItems();
        AssignSessionComboBox.addItem("");
        AssignCourseComboBox.addItem("");
        AssignProfessorComboBox.addItem("");
        for(String s : SessionList){
            AssignSessionComboBox.addItem(s);
        }
        for(String s : CourseList){
            AssignCourseComboBox.addItem(s);
        }
        for(String s : ProfessorList){
            AssignProfessorComboBox.addItem(s);
        }
        DefaultTableModel table = (DefaultTableModel)AssignTable.getModel();
        table.getDataVector().removeAllElements();
        for(int i=0;i<AssignList.size();i++){
            String s = AssignList.get(i).get(0);
            String c = AssignList.get(i).get(1);
            String p = AssignList.get(i).get(2);
            String Data[] = {s,c,p};
            table.addRow(Data);
        }
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for(int i=0;i<3;i++){
            AssignTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        AssignTable.getTableHeader().setDefaultRenderer(centerRenderer);
    }//GEN-LAST:event_jLabel7MouseClicked

    private void Add2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Add2ActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        int row = SessionTable.getSelectedRow();
        if(row == -1){
            JOptionPane.showMessageDialog(this, "Please First Select The Row !");
        }
        else{
            DefaultTableModel table = (DefaultTableModel)SessionTable.getModel();
            String session = table.getValueAt(row, 0).toString();
            S.remove(session);
            SessionList.remove(session);
            SessionName.setText("");
            table.removeRow(row);
            JOptionPane.showMessageDialog(this, "Succesfully Deleted !");
            System.out.print(SessionList);
        }
    }//GEN-LAST:event_Add2ActionPerformed

    private void jLabel4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseEntered
        // TODO add your handling code here:
        jLabel4.setForeground(Color.YELLOW);
    }//GEN-LAST:event_jLabel4MouseEntered

    private void jLabel4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseExited
        // TODO add your handling code here:
        jLabel4.setForeground(Color.WHITE);
    }//GEN-LAST:event_jLabel4MouseExited

    private void jLabel2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseEntered
        // TODO add your handling code here:
        jLabel2.setForeground(Color.YELLOW);
    }//GEN-LAST:event_jLabel2MouseEntered

    private void jLabel2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseExited
        // TODO add your handling code here:
        jLabel2.setForeground(Color.WHITE);
    }//GEN-LAST:event_jLabel2MouseExited

    private void SaveAndExitMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SaveAndExitMouseEntered
        // TODO add your handling code here:
        SaveAndExit.setForeground(Color.YELLOW);
    }//GEN-LAST:event_SaveAndExitMouseEntered

    private void SaveAndExitMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SaveAndExitMouseExited
        // TODO add your handling code here:
        SaveAndExit.setForeground(Color.WHITE);
    }//GEN-LAST:event_SaveAndExitMouseExited

    private void jLabel7MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseEntered
        // TODO add your handling code here:
        jLabel7.setForeground(Color.YELLOW);
    }//GEN-LAST:event_jLabel7MouseEntered

    private void jLabel7MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseExited
        // TODO add your handling code here:
        jLabel7.setForeground(Color.WHITE);
    }//GEN-LAST:event_jLabel7MouseExited

    private void jLabel3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseEntered
        // TODO add your handling code here:
        jLabel3.setForeground(Color.YELLOW);
    }//GEN-LAST:event_jLabel3MouseEntered

    private void jLabel3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseExited
        // TODO add your handling code here:
        jLabel3.setForeground(Color.WHITE);
    }//GEN-LAST:event_jLabel3MouseExited

    private void jLabel5MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseExited
        // TODO add your handling code here:
        jLabel5.setForeground(Color.WHITE);
    }//GEN-LAST:event_jLabel5MouseExited

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        // TODO add your handling code here
        Menu.setSelectedIndex(1);
    }//GEN-LAST:event_jLabel5MouseClicked

    private void Add3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Add3ActionPerformed
        // TODO add your handling code here:
        if("".equals(SlotName.getText())){
            JOptionPane.showMessageDialog(this, "Fill Up The Form !");
        }
        else{
            String Data[] = {SlotName.getText()};
            DefaultTableModel table = (DefaultTableModel)SlotTable.getModel();
            table.addRow(Data);
            SlotList.add(SlotName.getText());
            SlotName.setText("");
            JOptionPane.showMessageDialog(this, "Succesfully Added !");
        }
    }//GEN-LAST:event_Add3ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        Menu.setSelectedIndex(6);
        DefaultTableModel table = (DefaultTableModel)SlotTable.getModel();
        table.getDataVector().removeAllElements();
        for(String s : SlotList){
            String[] Ses = {s};
            table.addRow(Ses);
        }
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        SlotTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        SlotTable.getTableHeader().setDefaultRenderer(centerRenderer);
    }//GEN-LAST:event_jButton10ActionPerformed

    private void AddDayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddDayActionPerformed
        // TODO add your handling code here:
        if("".equals(DayName.getText())){
            JOptionPane.showMessageDialog(this, "Fill Up The Form !");
        }
        else{
            String Data[] = {DayName.getText()};
            DefaultTableModel table = (DefaultTableModel)DayTable.getModel();
            table.addRow(Data);
            String d_name = DayName.getText();
            DayList.add(d_name);
            DayName.setText("");
            JOptionPane.showMessageDialog(this, "Succesfully Added !");
            System.out.println(DayList);
        }
    }//GEN-LAST:event_AddDayActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
        Menu.setSelectedIndex(7);
        DefaultTableModel table = (DefaultTableModel)DayTable.getModel();
        table.getDataVector().removeAllElements();
        for(String s : DayList){
            String[] Ses = {s};
            table.addRow(Ses);
        }
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        DayTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        DayTable.getTableHeader().setDefaultRenderer(centerRenderer);
    }//GEN-LAST:event_jButton11ActionPerformed

    private void AddRoomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddRoomActionPerformed
        // TODO add your handling code here:
        if(DayList.size()==0 && SlotList.size() == 0){
            JOptionPane.showMessageDialog(this,"Add Slots and Day !!");
        }
        if("".equals(RoomName.getText()) || RoomCategoryComboBox.getSelectedItem().toString() == "Select"){
            JOptionPane.showMessageDialog(this, "Fill Up The Form !");
        }
        else{
            String Data[] = {RoomName.getText(),RoomCategoryComboBox.getSelectedItem().toString()};
            DefaultTableModel table = (DefaultTableModel)RoomTable.getModel();
            table.addRow(Data);
            String r_name = RoomName.getText();
            String r_category = RoomCategoryComboBox.getSelectedItem().toString();
            Room x = new Room(r_name,r_category);
            x.setSize(DayList.size(), SlotList.size());
            R.put(r_name,x);
            if(r_category == "Class Room") ClassRoomList.add(r_name);
            else LabRoomList.add(r_name);
            RoomName.setText("");
            System.out.println(R);
            System.out.println(ClassRoomList);
            System.out.println(LabRoomList);
            JOptionPane.showMessageDialog(this, "Succesfully Added !");
        }
    }//GEN-LAST:event_AddRoomActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here:
        Menu.setSelectedIndex(8);
        DefaultTableModel table = (DefaultTableModel)RoomTable.getModel();
        table.getDataVector().removeAllElements();
        for(String s : ClassRoomList){
            String[] Ses = {s,R.get(s).category};
            table.addRow(Ses);
        }
        for(String s : LabRoomList){
            String[] Ses = {s,R.get(s).category};
            table.addRow(Ses);
        }
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        RoomTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        RoomTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        RoomTable.getTableHeader().setDefaultRenderer(centerRenderer);
    }//GEN-LAST:event_jButton12ActionPerformed

    private void AddCourseRoomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddCourseRoomActionPerformed
        // TODO add your handling code here:
        Boolean test = "".equals(CourseComboBox.getSelectedItem().toString());
        test = test || "".equals(RoomComboBox.getSelectedItem().toString());
        if(test){
            JOptionPane.showMessageDialog(this, "Fill Up The Form !");
        }
        else{
            String course =  CourseComboBox.getSelectedItem().toString();
            String room = RoomComboBox.getSelectedItem().toString();
            String Data[] = {course,room};
            DefaultTableModel table = (DefaultTableModel)CourseRoomTable.getModel();
            table.addRow(Data);
            Course x = CR.get(course);
            x.roomlist.add(room);
            CourseComboBox.getModel().setSelectedItem("");
            RoomComboBox.getModel().setSelectedItem("");
            System.out.println(x + " - > " + x.roomlist);
            JOptionPane.showMessageDialog(this, "Succesfully Added !");
        }
    }//GEN-LAST:event_AddCourseRoomActionPerformed

    private void DeleteCourseRoomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteCourseRoomActionPerformed
        // TODO add your handling code here:
        int row = CourseRoomTable.getSelectedRow();
        if(row == -1){
            JOptionPane.showMessageDialog(this, "Please First Select The Row !");
        }
        else{
            DefaultTableModel table = (DefaultTableModel)CourseRoomTable.getModel();
            String course = table.getValueAt(row, 0).toString();
            String room = table.getValueAt(row, 1).toString();
            CR.get(course).roomlist.remove(room);
            table.removeRow(row);
            JOptionPane.showMessageDialog(this, "Succesfully Deleted !");
        }
    }//GEN-LAST:event_DeleteCourseRoomActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        Menu.setSelectedIndex(9);
        RoomComboBox.removeAllItems();
        CourseComboBox.removeAllItems();
        RoomComboBox.addItem("");
        CourseComboBox.addItem("");
        for(String s : ClassRoomList){
            RoomComboBox.addItem(s);
        }
        for(String s : LabRoomList){
            RoomComboBox.addItem(s);
        }
        for(String s : CourseList){
            CourseComboBox.addItem(s);
        }
        DefaultTableModel table = (DefaultTableModel)CourseRoomTable.getModel();
        table.getDataVector().removeAllElements();
        for(String s : CourseList){
            Course x = CR.get(s);
            System.out.println(x + " - > " + x.roomlist);
            for(String r : x.roomlist){
                String Data[] = {s,r};
                table.addRow(Data);
            }
        }
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for(int i=0;i<2;i++){
            CourseRoomTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        CourseRoomTable.getTableHeader().setDefaultRenderer(centerRenderer);
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
        Menu.setSelectedIndex(11);
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        // TODO add your handling code here:
        Menu.setSelectedIndex(10);
    }//GEN-LAST:event_jButton14ActionPerformed

    private void AddsignActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddsignActionPerformed
        // TODO add your handling code here:
        Sign = SignName.getText();
        SignName.setText("");
        JOptionPane.showMessageDialog(this, "Signature Set !");
        System.out.print(Sign);
    }//GEN-LAST:event_AddsignActionPerformed

    private void AddtitleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddtitleActionPerformed
        // TODO add your handling code here:
        Title = TitleName.getText();
        TitleName.setText("");
        JOptionPane.showMessageDialog(this, "Title Set !");
    }//GEN-LAST:event_AddtitleActionPerformed

    private void SessionAddMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SessionAddMouseClicked
        // TODO add your handling code here:
        if(SlotList.size()==0 || DayList.size()==0){
            JOptionPane.showMessageDialog(this, "Customize Routine First. Set Solts and Days!");
            SessionName.setText("");
            return;
        }
        if("".equals(SessionName.getText())){
            JOptionPane.showMessageDialog(this, "Fill Up The Form !");
        }
        else{
            String Data[] = {SessionName.getText()};
            DefaultTableModel table = (DefaultTableModel)SessionTable.getModel();
            table.addRow(Data);
            String s_name = SessionName.getText();
            Session x = new Session(s_name);
            x.setSize(DayList.size(), SlotList.size());
            x.ShowRoutine();
            S.put(s_name,x);
            SessionList.add(s_name);
            SessionName.setText("");
            JOptionPane.showMessageDialog(this, "Succesfully Added !");
        }
    }//GEN-LAST:event_SessionAddMouseClicked

    private void SessionTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SessionTableMouseClicked
        // TODO add your handling code here:
        DefaultTableModel table = (DefaultTableModel)SessionTable.getModel();
        int row = SessionTable.getSelectedRow();
        String session = table.getValueAt(row, 0).toString();
        SessionName.setText(session);
    }//GEN-LAST:event_SessionTableMouseClicked

    private void Add1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Add1ActionPerformed
        // TODO add your handling code here:
        System.out.print(S);
        int row = SessionTable.getSelectedRow();
        if(row == -1){
            JOptionPane.showMessageDialog(this, "Please First Select The Row !");
            return;
        }
        if("".equals(SessionName.getText())){
            JOptionPane.showMessageDialog(this, "Fill Up The Form !");
            return;
        }
        if(SessionTable.getSelectedRowCount()>1){
            JOptionPane.showMessageDialog(this, "Select Only One Row For Update !");
            return;
        }
        DefaultTableModel table = (DefaultTableModel)SessionTable.getModel();
        String session = table.getValueAt(row, 0).toString();
        String Name = SessionName.getText();
        table.setValueAt(Name, row, 0);
        for(int i=0;i<SessionList.size();i++){
            String s = SessionList.get(i);
            if(s.equals(session)){
                SessionList.set(i, Name);
            }
        }
        Session s = new Session(Name);
        s.setSize(DayList.size(), SlotList.size());
        S.remove(session);
        S.put(Name, s);
        for(String c : CourseList){
            Course x = CR.get(c);
            if(x.Session == session){
                x.Session = Name;
            }
        }
        s.ShowRoutine();
        SessionName.setText("");
        JOptionPane.showMessageDialog(this, "Successfully Updated !");
    }//GEN-LAST:event_Add1ActionPerformed

    private void SlotTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SlotTableMouseClicked
        // TODO add your handling code here:
        DefaultTableModel table = (DefaultTableModel)SlotTable.getModel();
        int row = SlotTable.getSelectedRow();
        if(row == -1) return;
        String slt = table.getValueAt(row, 0).toString();
        SlotName.setText(slt);
    }//GEN-LAST:event_SlotTableMouseClicked

    private void Add5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Add5ActionPerformed
        // TODO add your handling code here:
        int row = SlotTable.getSelectedRow();
        if(row == -1){
            JOptionPane.showMessageDialog(this, "Please First Select The Row !");
        }
        else{
            DefaultTableModel table = (DefaultTableModel)SlotTable.getModel();
            String slt = table.getValueAt(row, 0).toString();
            SlotList.remove(slt);
            SlotName.setText("");
            table.removeRow(row);
            JOptionPane.showMessageDialog(this, "Succesfully Deleted !");
            System.out.print(SlotList);
        }
    }//GEN-LAST:event_Add5ActionPerformed

    private void Add4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Add4ActionPerformed
        // TODO add your handling code here:
        int row = SlotTable.getSelectedRow();
        if(row == -1){
            JOptionPane.showMessageDialog(this, "Please First Select The Row !");
            return;
        }
        if("".equals(SlotName.getText())){
            JOptionPane.showMessageDialog(this, "Fill Up The Form !");
            return;
        }
        if(SlotTable.getSelectedRowCount()>1){
            JOptionPane.showMessageDialog(this, "Select Only One Row For Update !");
            return;
        }
        DefaultTableModel table = (DefaultTableModel)SlotTable.getModel();
        String slt = table.getValueAt(row, 0).toString();
        String Name = SlotName.getText();
        table.setValueAt(Name, row, 0);
        for(int i=0;i<SlotList.size();i++){
            String s = SlotList.get(i);
            if(s.equals(slt)){
                SlotList.set(i, Name);
            }
        }
        SlotName.setText("");
        //System.out.println(SlotList);
        JOptionPane.showMessageDialog(this, "Successfully Updated !");
        System.out.println(SlotList);
    }//GEN-LAST:event_Add4ActionPerformed

    private void DayTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DayTableMouseClicked
        // TODO add your handling code here:
        DefaultTableModel table = (DefaultTableModel)DayTable.getModel();
        int row = DayTable.getSelectedRow();
        if(row == -1) return;
        String d = table.getValueAt(row, 0).toString();
        DayName.setText(d);

    }//GEN-LAST:event_DayTableMouseClicked

    private void DeleteDayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteDayActionPerformed
        // TODO add your handling code here:
        int row = DayTable.getSelectedRow();
        if(row == -1){
            JOptionPane.showMessageDialog(this, "Please First Select The Row !");
        }
        else{
            DefaultTableModel table = (DefaultTableModel)DayTable.getModel();
            String d = table.getValueAt(row, 0).toString();
            DayList.remove(d);
            DayName.setText("");
            table.removeRow(row);
            JOptionPane.showMessageDialog(this, "Succesfully Deleted !");
            System.out.print(DayList);
        }
    }//GEN-LAST:event_DeleteDayActionPerformed

    private void UpdateDayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateDayActionPerformed
        // TODO add your handling code here:
        System.out.print(DayList);
        int row = DayTable.getSelectedRow();
        if(row == -1){
            JOptionPane.showMessageDialog(this, "Please First Select The Row !");
            return;
        }
        if("".equals(DayName.getText())){
            JOptionPane.showMessageDialog(this, "Fill Up The Form !");
            return;
        }
        if(DayTable.getSelectedRowCount()>1){
            JOptionPane.showMessageDialog(this, "Select Only One Row For Update !");
            return;
        }
        DefaultTableModel table = (DefaultTableModel)DayTable.getModel();
        String d = table.getValueAt(row, 0).toString();
        String Name = DayName.getText();
        table.setValueAt(Name, row, 0);
        for(int i=0;i<DayList.size();i++){
            String s = DayList.get(i);
            if(s.equals(d)){
                DayList.set(i, Name);
            }
        }
        DayName.setText("");
        JOptionPane.showMessageDialog(this, "Successfully Updated !");
        System.out.println(DayList);
    }//GEN-LAST:event_UpdateDayActionPerformed

    private void SessionComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SessionComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SessionComboBoxActionPerformed

    private void WeekComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_WeekComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_WeekComboBoxActionPerformed

    private void Add_CourseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Add_CourseActionPerformed
        // TODO add your handling code here:
        Boolean test = "".equals(CourseName.getText());
        test = test || "".equals(CourseCode.getText());
        test = test || "".equals(SlotComboBox.getSelectedItem().toString());
        test = test || "".equals(SessionComboBox.getSelectedItem().toString());
        test = test || "".equals(WeekComboBox.getSelectedItem().toString());
        if(test){
            JOptionPane.showMessageDialog(this, "Fill Up The Form !");
        }
        else{
            String c_name = CourseName.getText();
            String c_code = CourseCode.getText();
            String slot_count =  SlotComboBox.getSelectedItem().toString();
            String session_name = SessionComboBox.getSelectedItem().toString();
            String perweekclass = WeekComboBox.getSelectedItem().toString();
            String Data[] = {c_name,c_code,slot_count,perweekclass,session_name};
            DefaultTableModel table = (DefaultTableModel)CourseTable.getModel();
            table.addRow(Data);
            Course x = new Course(c_name,c_code,slot_count,perweekclass,session_name);
            CR.put(c_code,x);
            Session s = S.get(session_name);
            s.Courses.add(c_code);
            CourseList.add(c_code);
            CourseName.setText("");
            CourseCode.setText("");
            SlotComboBox.getModel().setSelectedItem("");
            SessionComboBox.getModel().setSelectedItem("");
            WeekComboBox.getModel().setSelectedItem("");
            JOptionPane.showMessageDialog(this, "Succesfully Added !");
        }
    }//GEN-LAST:event_Add_CourseActionPerformed

    private void CourseTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CourseTableMouseClicked
        // TODO add your handling code here:
        DefaultTableModel table = (DefaultTableModel)CourseTable.getModel();
        int row = CourseTable.getSelectedRow();
        if(row == -1) return;
        String c_name = table.getValueAt(row, 0).toString();
        String c_code = table.getValueAt(row, 1).toString();
        String slot_count =  table.getValueAt(row, 2).toString();
        String session_name = table.getValueAt(row, 4).toString();
        String perweekclass = table.getValueAt(row, 3).toString();
        CourseName.setText(c_name);
        CourseCode.setText(c_code);
        SlotComboBox.getModel().setSelectedItem(slot_count);
        SessionComboBox.getModel().setSelectedItem(session_name);
        WeekComboBox.getModel().setSelectedItem(perweekclass);
    }//GEN-LAST:event_CourseTableMouseClicked

    private void UpdateCourseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateCourseActionPerformed
        // TODO add your handling code here:
        int row = CourseTable.getSelectedRow();
        if(row == -1){
            JOptionPane.showMessageDialog(this, "Please First Select The Row !");
            return;
        }
        Boolean test = "".equals(CourseName.getText());
        test = test || "".equals(CourseCode.getText());
        test = test || "".equals(SlotComboBox.getSelectedItem().toString());
        test = test || "".equals(SessionComboBox.getSelectedItem().toString());
        test = test || "".equals(WeekComboBox.getSelectedItem().toString());
        if(test){
            JOptionPane.showMessageDialog(this, "Fill Up The Form !");
            return;
        }
        if(SessionTable.getSelectedRowCount()>1){
            JOptionPane.showMessageDialog(this, "Select Only One Row For Update !");
            return;
        }
        DefaultTableModel table = (DefaultTableModel)CourseTable.getModel();
        String c_name = table.getValueAt(row, 0).toString();
        String c_code = table.getValueAt(row, 1).toString();
        String slot_count =  table.getValueAt(row, 2).toString();
        String session_name = table.getValueAt(row, 4).toString();
        String perweekclass = table.getValueAt(row, 3).toString();
        String Code = CourseCode.getText();
        String PerWeek = WeekComboBox.getSelectedItem().toString();
        String Slots = SlotComboBox.getSelectedItem().toString();
        String Session = SessionComboBox.getSelectedItem().toString();
        if(c_code == CourseCode.getText()){
            Course x = CR.get(c_code);
            x.Code = CourseCode.getText();
            x.PerWeek = WeekComboBox.getSelectedItem().toString();
            x.Slots = SlotComboBox.getSelectedItem().toString();
            x.Session = SessionComboBox.getSelectedItem().toString();
            for(String s : SessionList){
                if(s == session_name){
                    Session ss = S.get(session_name);
                    ss.Courses.remove(c_code);
                }
            }
            for(String s : SessionList){
                if(s == SessionComboBox.getSelectedItem().toString()){
                    Session ss = S.get(SessionComboBox.getSelectedItem().toString());
                    ss.Courses.add(Code);
                }
            }
        }
        else{
            Course x = new Course(CourseName.getText(),Code,Slots,PerWeek,Session);
            CR.remove(c_code);
            CR.put(Code, x);
            CourseList.remove(c_code);
            CourseList.add(Code);
        }
        table.setValueAt(CourseName.getText(), row, 0);
        table.setValueAt(Code, row, 1);
        table.setValueAt(Slots, row, 2);
        table.setValueAt(PerWeek, row, 3);
        table.setValueAt(Session, row, 4);
        CourseName.setText("");
        CourseCode.setText("");
        SlotComboBox.getModel().setSelectedItem("");
        SessionComboBox.getModel().setSelectedItem("");
        WeekComboBox.getModel().setSelectedItem("");
        System.out.println(CourseList);
        JOptionPane.showMessageDialog(this, "Successfully Updated !");
    }//GEN-LAST:event_UpdateCourseActionPerformed

    private void DeleteRoomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteRoomActionPerformed
        // TODO add your handling code here:
        int row = RoomTable.getSelectedRow();
        if(row == -1){
            JOptionPane.showMessageDialog(this, "Please First Select The Row !");
        }
        else{
            DefaultTableModel table = (DefaultTableModel)RoomTable.getModel();
            String room = table.getValueAt(row, 0).toString();
            String category = table.getValueAt(row, 1).toString();
            R.remove(room);
            if(category == "Class Room") ClassRoomList.remove(room);
            else LabRoomList.remove(room);
            RoomName.setText("");
            table.removeRow(row);
            JOptionPane.showMessageDialog(this, "Succesfully Deleted !");
            System.out.print(ClassRoomList);
            System.out.println(LabRoomList);
        }
    }//GEN-LAST:event_DeleteRoomActionPerformed

    private void ProfessorAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ProfessorAddActionPerformed
        // TODO add your handling code here:
        if(SlotList.size() == 0 || DayList.size() == 0){
            JOptionPane.showMessageDialog(this, "Customize Routine First . Add Day And Slots");
            return;
        }
        String name = ProfessorName.getText();
        String code = ProfessorCode.getText();
        String jobyear = JobYear.getText();
        String number = ProfessorNumber.getText();
        Boolean test = "".equals(ProfessorName.getText());
        test = test || "".equals(ProfessorCode.getText());
        test = test || "".equals(ProfessorNumber.getText());
        test = test || "".equals(JobYear.getText());
        if(test){
            JOptionPane.showMessageDialog(this, "Fill Up The Form !");
        }
        else{
            String Data[] = {name,jobyear,code,number};
            DefaultTableModel table = (DefaultTableModel)ProfessorTable.getModel();
            table.addRow(Data);
            Professor x = new Professor(name,code,number,jobyear);
            x.setSize(DayList.size(), SlotList.size());
            x.ShowRoutine();
            PR.put(code,x);
            ProfessorList.add(code);
            ProfessorName.setText("");
            ProfessorCode.setText("");
            ProfessorNumber.setText("");
            JobYear.setText("");
            System.out.println(ProfessorList);
            System.out.println(PR);
            JOptionPane.showMessageDialog(this, "Succesfully Added !");
        }
    }//GEN-LAST:event_ProfessorAddActionPerformed

    private void ProfessorTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ProfessorTableMouseClicked
        // TODO add your handling code here:
        DefaultTableModel table = (DefaultTableModel)ProfessorTable.getModel();
        int row = ProfessorTable.getSelectedRow();
        if(row==-1) return;
        String name = table.getValueAt(row, 0).toString();
        String jobyear = table.getValueAt(row, 1).toString();
        String code = table.getValueAt(row, 2).toString();
        String number = table.getValueAt(row, 3).toString();
        ProfessorName.setText(name);
        ProfessorCode.setText(code);
        ProfessorNumber.setText(number);
        JobYear.setText(jobyear);
    }//GEN-LAST:event_ProfessorTableMouseClicked

    private void ProfessorUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ProfessorUpdateActionPerformed
        // TODO add your handling code here:
        int row = ProfessorTable.getSelectedRow();
        if(row == -1){
            JOptionPane.showMessageDialog(this, "Please First Select The Row !");
            return;
        }
        Boolean test = "".equals(ProfessorName.getText());
        test = test || "".equals(ProfessorCode.getText());
        test = test || "".equals(ProfessorNumber.getText());
        test = test || "".equals(JobYear.getText());
        if(test){
            JOptionPane.showMessageDialog(this, "Fill Up The Form !");
            return;
        }
        if(ProfessorTable.getSelectedRowCount()>1){
            JOptionPane.showMessageDialog(this, "Select Only One Row For Update !");
            return;
        }
        DefaultTableModel table = (DefaultTableModel)ProfessorTable.getModel();
        String name = table.getValueAt(row, 0).toString();
        String code = table.getValueAt(row, 2).toString();
        String jobyear =  table.getValueAt(row, 1).toString();
        String number = table.getValueAt(row, 3).toString();
        String newcode = ProfessorCode.getText();
        String newname = ProfessorName.getText();
        String newnumber = ProfessorNumber.getText();
        String newjobyear = JobYear.getText();
        if(code == newcode){
            Professor x = PR.get(code);
            x.code = newcode;
            x.name = newname;
            x.number = newnumber;
            x.jobyear = newjobyear;
        }
        else{
            Professor x = new Professor(newname,newcode,newnumber,newjobyear);
            PR.remove(code);
            x.setSize(DayList.size(), SlotList.size());
            PR.put(newcode, x);
            for(int i=0;i<ProfessorList.size();i++){
                if(ProfessorList.get(i) == code){
                    //System.out.print("Hwllo");
                    ProfessorList.set(i,newcode);
                    //System.out.print(CourseList.get(i));
                }
            }
        }
        table.setValueAt(newname, row, 0);
        table.setValueAt(newjobyear, row, 1);
        table.setValueAt(newcode, row, 2);
        table.setValueAt(newnumber, row, 3);
        ProfessorName.setText("");
        ProfessorCode.setText("");
        ProfessorNumber.setText("");
        JobYear.setText("");
        System.out.println(ProfessorList);
        System.out.println(PR);
        JOptionPane.showMessageDialog(this, "Successfully Updated !");
    }//GEN-LAST:event_ProfessorUpdateActionPerformed

    private void DeleteProfessorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteProfessorActionPerformed
        // TODO add your handling code here:
        int row = ProfessorTable.getSelectedRow();
        if(row == -1){
            JOptionPane.showMessageDialog(this, "Please First Select The Row !");
        }
        else{
            DefaultTableModel table = (DefaultTableModel)ProfessorTable.getModel();
            String code = table.getValueAt(row, 2).toString();
            PR.remove(code);
            ProfessorList.remove(code);
            ProfessorName.setText("");
            ProfessorCode.setText("");
            ProfessorNumber.setText("");
            JobYear.setText("");
            table.removeRow(row);
            JOptionPane.showMessageDialog(this, "Succesfully Deleted !");
            System.out.println(ProfessorList);
            System.out.println(PR);
        }
    }//GEN-LAST:event_DeleteProfessorActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        Boolean test = "".equals(AssignSessionComboBox.getSelectedItem().toString());
        test = test || "".equals(AssignCourseComboBox.getSelectedItem().toString());
        test = test || "".equals(AssignProfessorComboBox.getSelectedItem().toString());
        if(test){
            JOptionPane.showMessageDialog(this, "Fill Up The Form !");
        }
        else{
            String s =  AssignSessionComboBox.getSelectedItem().toString();
            String c = AssignCourseComboBox.getSelectedItem().toString();
            String p = AssignProfessorComboBox.getSelectedItem().toString();
            Course x = CR.get(c);
            if(x.assign){
                JOptionPane.showMessageDialog(this, "Already Assigned !");
                return;
            }
            ArrayList<String> temp = new ArrayList<String>();
            temp.add(s);
            temp.add(c);
            temp.add(p);
            AssignList.add(temp);
            x.assign = true;
            x.ProfessorCode = p;
            Professor pr = PR.get(p);
            pr.courses.add(c);
            String Data[] = {s,c,p};
            DefaultTableModel table = (DefaultTableModel)AssignTable.getModel();
            table.addRow(Data);
            
            AssignCourseComboBox.getModel().setSelectedItem("");
            AssignSessionComboBox.getModel().setSelectedItem("");
            AssignProfessorComboBox.getModel().setSelectedItem("");
            JOptionPane.showMessageDialog(this, "Succesfully Assigned !");
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void SearchCourseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SearchCourseMouseClicked
        // TODO add your handling code here:
        String session_name = AssignSessionComboBox.getSelectedItem().toString();
        if("".equals(session_name)){
            JOptionPane.showMessageDialog(this, "Select Session First !");
        }
        else{
            AssignCourseComboBox.removeAllItems();
            Session x = S.get(session_name);
            for(String s : x.Courses){
                AssignCourseComboBox.addItem(s);
            }
        }
    }//GEN-LAST:event_SearchCourseMouseClicked

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        int row = AssignTable.getSelectedRow();
        if(row == -1){
            JOptionPane.showMessageDialog(this, "Please First Select The Row !");
        }
        else{
            DefaultTableModel table = (DefaultTableModel)AssignTable.getModel();
            String p = table.getValueAt(row, 2).toString();
            String c = table.getValueAt(row, 1).toString();
            String s = table.getValueAt(row, 0).toString();
            Professor pr = PR.get(p);
            pr.courses.remove(c);
            CR.get(c).assign = false;
            CR.get(c).ProfessorCode = null;
            table.removeRow(row);
            ArrayList<String> temp = new ArrayList<String>();
            temp.add(s);
            temp.add(c);
            temp.add(p);
            AssignList.remove(temp);
            JOptionPane.showMessageDialog(this, "Succesfully Deleted !");
            System.out.println(AssignList);
            System.out.println(PR.get(p).courses);
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void BusyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BusyActionPerformed
        // TODO add your handling code here:
        Menu.setSelectedIndex(12);
        BusyDayComboBox.removeAllItems();
        BusySlotComboBox.removeAllItems();
        BusyDayComboBox.addItem("Everyday");
        BusySlotComboBox.addItem("AllSlots");
        for(String d : DayList){
            BusyDayComboBox.addItem(d);
        }
        for(String d : SlotList){
            BusySlotComboBox.addItem(d);
        }        
    }//GEN-LAST:event_BusyActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        // TODO add your handling code here:
        if(CategoryComboBox.getSelectedItem().toString() == "Select" || SelectComboBox.getSelectedItem() == null){
            JOptionPane.showMessageDialog(this, "Select Option First !");
            return;
        }
        if(BusyDayComboBox.getSelectedItem().toString() == "Everyday" && BusySlotComboBox.getSelectedItem().toString() == "AllSlots"){
            JOptionPane.showMessageDialog(this, "Invalid Busy Schedules!");
            return;
        }
        String rt[][] = new String[DayList.size()][SlotList.size()];
        if(CategoryComboBox.getSelectedItem().toString() == "Professor"){
            rt = PR.get(SelectComboBox.getSelectedItem().toString()).routine;
        }
        else{
            rt = S.get(SelectComboBox.getSelectedItem().toString()).routine;
        }
        if(BusyDayComboBox.getSelectedItem().toString() == "Everyday" && BusySlotComboBox.getSelectedItem().toString() != "AllSlots"){
            int col = SlotList.indexOf(BusySlotComboBox.getSelectedItem().toString());
            System.out.println(col);
            for(int row = 0;row<DayList.size();row++){
                rt[row][col] = "BUSY";
            }
        }
        else if(BusyDayComboBox.getSelectedItem().toString() != "Everyday" && BusySlotComboBox.getSelectedItem().toString() == "AllSlots"){
            int row = DayList.indexOf(BusyDayComboBox.getSelectedItem().toString());
            System.out.println(row);
            for(int col = 0;col<SlotList.size();col++){
                rt[row][col] = "BUSY";
            } 
        }
        else{
            int row = DayList.indexOf(BusyDayComboBox.getSelectedItem().toString());
            int col = SlotList.indexOf(BusySlotComboBox.getSelectedItem().toString());
            rt[row][col] = "BUSY";
        }
        JOptionPane.showMessageDialog(this, "Set Busy Successfully !");
        if(CategoryComboBox.getSelectedItem().toString() == "Professor"){
            Professor p = PR.get(SelectComboBox.getSelectedItem().toString());
            p.ShowRoutine();
        }
        else{
            Session p = S.get(SelectComboBox.getSelectedItem().toString());
            p.ShowRoutine();
        }
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        // TODO add your handling code here:
        if(CategoryComboBox.getSelectedItem().toString() == "Select" || SelectComboBox.getSelectedItem() == null){
            JOptionPane.showMessageDialog(this, "Select Option First !");
            return;
        }
        if(BusyDayComboBox.getSelectedItem().toString() == "Everyday" && BusySlotComboBox.getSelectedItem().toString() == "AllSlots"){
            if(CategoryComboBox.getSelectedItem().toString() == "Professor"){
                Professor p = PR.get(SelectComboBox.getSelectedItem().toString());
                p.ClearRoutine();
            }
            else{
                Session p = S.get(SelectComboBox.getSelectedItem().toString());
                p.ClearRoutine();
            }
            JOptionPane.showMessageDialog(this, "Reset Successfully !");
            return;
        }
        String rt[][] = new String[DayList.size()][SlotList.size()];
        if(CategoryComboBox.getSelectedItem().toString() == "Professor"){
            rt = PR.get(SelectComboBox.getSelectedItem().toString()).routine;
        }
        else{
            rt = S.get(SelectComboBox.getSelectedItem().toString()).routine;
        }
        if(BusyDayComboBox.getSelectedItem().toString() == "Everyday" && BusySlotComboBox.getSelectedItem().toString() != "AllSlots"){
            int col = SlotList.indexOf(BusySlotComboBox.getSelectedItem().toString());
            System.out.println(col);
            for(int row = 0;row<DayList.size();row++){
                rt[row][col] = null;
            }
        }
        else if(BusyDayComboBox.getSelectedItem().toString() != "Everyday" && BusySlotComboBox.getSelectedItem().toString() == "AllSlots"){
            int row = DayList.indexOf(BusyDayComboBox.getSelectedItem().toString());
            System.out.println(row);
            for(int col = 0;col<SlotList.size();col++){
                rt[row][col] = null;
            } 
        }
        else{
            int row = DayList.indexOf(BusyDayComboBox.getSelectedItem().toString());
            int col = SlotList.indexOf(BusySlotComboBox.getSelectedItem().toString());
            rt[row][col] = null;
        }
        JOptionPane.showMessageDialog(this, "Reset Successfully !");
        if(CategoryComboBox.getSelectedItem().toString() == "Professor"){
            Professor p = PR.get(SelectComboBox.getSelectedItem().toString());
            p.ShowRoutine();
        }
        else{
            Session p = S.get(SelectComboBox.getSelectedItem().toString());
            p.ShowRoutine();
        }
    }//GEN-LAST:event_jButton16ActionPerformed

    private void CategoryComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_CategoryComboBoxItemStateChanged
        // TODO add your handling code here:
        if(CategoryComboBox.getSelectedItem().toString() != "Select"){
            SelectComboBox.removeAllItems();
            if(CategoryComboBox.getSelectedItem().toString() == "Professor"){
                label001.setText("Professor Code");
                for(String x : ProfessorList){
                    SelectComboBox.addItem(x);
                }
            }
            else{
                label001.setText("Sessions");
                for(String x : SessionList){
                    SelectComboBox.addItem(x);
                }
            }
        }
    }//GEN-LAST:event_CategoryComboBoxItemStateChanged

    private void BusySlotComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BusySlotComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BusySlotComboBoxActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton17ActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton18ActionPerformed

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton19ActionPerformed

    private void jButton21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton21ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton21ActionPerformed

    private void jButton22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton22ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton22ActionPerformed

    private void Busy1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Busy1ActionPerformed
        // TODO add your handling code here:
        Generate();
    }//GEN-LAST:event_Busy1ActionPerformed

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        // TODO add your handling code here:
        Menu.setSelectedIndex(13);
    }//GEN-LAST:event_jLabel3MouseClicked

    private void SessionAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SessionAddActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SessionAddActionPerformed

    private void jLabel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseClicked
        // TODO add your handling code here:
        Menu.setSelectedIndex(3);
        DefaultTableModel table = (DefaultTableModel)ProfessorTable.getModel();
        table.getDataVector().removeAllElements();
        for(String s : ProfessorList){
            Professor x = PR.get(s);
            String[] pr = {x.name,x.jobyear,x.code,x.number};
            table.addRow(pr);
        }
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for(int i=0;i<4;i++){
            ProfessorTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        ProfessorTable.getTableHeader().setDefaultRenderer(centerRenderer);
    }//GEN-LAST:event_jLabel12MouseClicked

    private void jLabel12MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel12MouseEntered

    private void jLabel12MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel12MouseExited
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                getData();
                new Dashboard().setVisible(true);
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Add1;
    private javax.swing.JButton Add2;
    private javax.swing.JButton Add3;
    private javax.swing.JButton Add4;
    private javax.swing.JButton Add5;
    private javax.swing.JPanel AddCourse;
    private javax.swing.JButton AddCourseRoom;
    private javax.swing.JButton AddDay;
    private javax.swing.JPanel AddProfessor;
    private javax.swing.JButton AddRoom;
    private javax.swing.JPanel AddSession;
    private javax.swing.JButton Add_Course;
    private javax.swing.JButton Addsign;
    private javax.swing.JButton Addtitle;
    private javax.swing.JPanel AssignBusySchedule;
    private javax.swing.JComboBox<String> AssignCourseComboBox;
    private javax.swing.JComboBox<String> AssignProfessorComboBox;
    private javax.swing.JPanel AssignRoutine;
    private javax.swing.JComboBox<String> AssignSessionComboBox;
    private javax.swing.JTable AssignTable;
    private javax.swing.JButton Busy;
    private javax.swing.JButton Busy1;
    private javax.swing.JComboBox<String> BusyDayComboBox;
    private javax.swing.JComboBox<String> BusySlotComboBox;
    private javax.swing.JComboBox<String> CategoryComboBox;
    private javax.swing.JTextField CourseCode;
    private javax.swing.JComboBox<String> CourseComboBox;
    private javax.swing.JTextField CourseName;
    private javax.swing.JPanel CourseRoom;
    private javax.swing.JTable CourseRoomTable;
    private javax.swing.JTable CourseTable;
    private javax.swing.JPanel CustomizeRoutine;
    private javax.swing.JPanel Day;
    private javax.swing.JTextField DayName;
    private javax.swing.JTable DayTable;
    private javax.swing.JButton DeleteCourse;
    private javax.swing.JButton DeleteCourseRoom;
    private javax.swing.JButton DeleteDay;
    private javax.swing.JButton DeleteProfessor;
    private javax.swing.JButton DeleteRoom;
    private javax.swing.JPanel Generate;
    private javax.swing.JTextField JobYear;
    private javax.swing.JPanel Main;
    private javax.swing.JTabbedPane Menu;
    private javax.swing.JButton ProfessorAdd;
    private javax.swing.JTextField ProfessorCode;
    private javax.swing.JTextField ProfessorName;
    private javax.swing.JTextField ProfessorNumber;
    private javax.swing.JTable ProfessorTable;
    private javax.swing.JButton ProfessorUpdate;
    private javax.swing.JPanel Room;
    private javax.swing.JComboBox<String> RoomCategoryComboBox;
    private javax.swing.JComboBox<String> RoomComboBox;
    private javax.swing.JTextField RoomName;
    private javax.swing.JTable RoomTable;
    private javax.swing.JLabel SaveAndExit;
    private javax.swing.JLabel SearchCourse;
    private javax.swing.JComboBox<String> SelectComboBox;
    private javax.swing.JButton SessionAdd;
    private javax.swing.JComboBox<String> SessionComboBox;
    private javax.swing.JTextField SessionName;
    private javax.swing.JTable SessionTable;
    private javax.swing.JPanel SetSignature;
    private javax.swing.JPanel SetTitle;
    private javax.swing.JTextArea SignName;
    private javax.swing.JPanel Slot;
    private javax.swing.JComboBox<String> SlotComboBox;
    private javax.swing.JTextField SlotName;
    private javax.swing.JTable SlotTable;
    private javax.swing.JTextArea TitleName;
    private javax.swing.JButton UpdateCourse;
    private javax.swing.JButton UpdateDay;
    private javax.swing.JComboBox<String> WeekComboBox;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton22;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JLabel label001;
    private javax.swing.JLabel professorNumber;
    private javax.swing.JLabel professorNumber2;
    // End of variables declaration//GEN-END:variables
}
