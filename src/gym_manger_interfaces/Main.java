package gym_manger_interfaces;
import com.mongodb.*;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main extends Application {
    MongoClient mongoClient = new MongoClient("localhost",27017);
    public static  DB db;
    public Cursor cursor;

    public DBCollection allCollection;
    public DBCollection collection3;
    public DBCollection collection2;
    public DBCollection collection1;
    public Over60Class over60Member;
    public StudentClass studentMember;
    public DefaultClass defaultMember;
    public Boolean checkdef;
    public Boolean checkstud;
    public Boolean checkOver60;
    public TableView Over60Table;
    public TableView defaultTable;
    public TableView StudentTable;
    public ComboBox comboSortMemberType;

    public Pane pnlSort;
    public Pane  pnlPrint;
    public Pane pnlHome;
    public Pane pnlAdd;
    public Pane pnlSave;
    public Label lblAddPnlFirstName;
    public TextField txtFldAddPnlFirstName;
    public Label lblAddPnlLastName;
    public TextField txtFldAddPnlLastName;
    public Label lblAddPnlStartDate;

    public ComboBox comboSaveMemberType;
    public ComboBox comboAddPnlMemberType;
    public ComboBox comboPrintMemberType;
    public Label lblAddPnlMemberType;
    public GridPane grdAddpnl1;
    public GridPane grdAddpnl2;
    public Label lblAge;
    public Label lblSchoolName;
    public TextField txtFldAge;
    public TextField txtFldSchoolName;
    public Button BtnRemoveSortPane;
    public ScrollPane ScrollPaneTable;
    public Button BtnSortPageSort;
    public Button BtnToAddPage;
    public Button BtnToSortPage;
    public Button BtnToPrintPage;
    public Button BtnToSavePage;
    public Button BtnAddMember;
    public Button BtnToSaveToTextFile;

    public TextField txtFldAddPnlStartDate;
    public TextField txtFldAddPnlStartMonth;
    public TextField txtFldAddPnlStartYear;

    public Label ErrorM1;
    public Label ErrorM2;
    public Label ErrorM3;
    public Label ErrorM4;
    public Label ErrorM5;
    public VBox VboxForMessages;

    public PrintWriter outputFile;
    public ScrollPane ScrollPrintPaneTable;
    public BasicDBObject obj1;
    public BasicDBObject obj2;
    public BasicDBObject obj3;
    public  String memberID;
    public Boolean checker;
    public String memberLastOrNot;
    public boolean firstMember=true;

    //setting up the string validation function
    public static boolean isNumber(String stringer){
        try
        {
            Integer.parseInt(stringer);

            return true;
        }catch(Exception e){
            return false;
        }
    }

    //setting up the day validation function
    public static boolean isValidDay(String stringer){
        Boolean checks= true;
            if(Integer.parseInt(stringer)>0 && Integer.parseInt(stringer)<=31) {
                checks= true;
            }
            else{
                checks=false;
        }
            return checks;
    }
// setting up the month validator
    public static boolean isValidMonth(String stringer){
        Boolean checkMonth= true;
        if(Integer.parseInt(stringer)>0 && Integer.parseInt(stringer)<=12) {
            checkMonth= true;
        }
        else{
            checkMonth=false;
        }
        return checkMonth;
    }
// setting up the year validator
    public static boolean isValidYear(String stringer){
        Boolean checkYear= true;
        if(Integer.parseInt(stringer)>2015 && Integer.parseInt(stringer)<=2100) {
            checkYear= true;
        }
        else{
            checkYear=false;
        }
        return checkYear;
    }
    public static boolean isString(String stringer){
        return stringer.matches("[a-zA-Z,]+");
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void start(Stage primaryStage) throws Exception{

// establishing database
        db = mongoClient.getDB("GymDatabases");
        //creating third anch pane
        AnchorPane anchThird=new AnchorPane();
        anchThird.setId("anchThird");
        anchThird.setPrefHeight(545);
        anchThird.setPrefWidth(172);

        //creating the navigation buttons

        //creating BtnToAddPage
        BtnToAddPage = new Button("       Add");
        BtnToAddPage.setPrefSize(159,59);
        BtnToAddPage.setStyle("-fx-text-fill: whitesmoke");
        BtnToAddPage.setLayoutX(-5);
        BtnToAddPage.setLayoutY(62);
        BtnToAddPage.setId("BtnsOnAnchThird");
        BtnToAddPage.setOnAction(e->{
            pnlAdd.toFront();
        });

        //creating BtnToAddPage icon settling
        Image ImageBtnToAddPage=new Image("images/add.png");
        ImageView imageViewBtnToAddPage= new ImageView(ImageBtnToAddPage);
        imageViewBtnToAddPage.setFitWidth(45);
        imageViewBtnToAddPage.setFitHeight(40);
        imageViewBtnToAddPage.setPickOnBounds(true);
        imageViewBtnToAddPage.setPreserveRatio(true);
        BtnToAddPage.setGraphic(imageViewBtnToAddPage);

        // creating button to navigate to sort page
        BtnToSortPage= new Button("       View");
        BtnToSortPage.setPrefSize(158,56);
        BtnToSortPage.setLayoutX(-8.0);
        BtnToSortPage.setLayoutY(165);
        BtnToSortPage.setId("BtnsOnAnchThird");
        BtnToSortPage.setStyle("-fx-text-fill: whitesmoke");
        BtnToSortPage.setOnAction(e->{
            pnlSort.toFront();
        });

        //creating BtnToSortPage icon settling
        Image ImageBtnToSortPage=new Image("images/sort-az.png");
        ImageView imageViewBtnToSortPage= new ImageView(ImageBtnToSortPage);
        imageViewBtnToSortPage.setFitWidth(58);
        imageViewBtnToSortPage.setFitHeight(38);
        imageViewBtnToSortPage.setPickOnBounds(true);
        imageViewBtnToSortPage.setPreserveRatio(true);
        BtnToSortPage.setGraphic(imageViewBtnToSortPage);

        //creating BtnToPrintPage
        BtnToPrintPage= new Button("       Print");
        BtnToPrintPage.setPrefSize(159,59);
        BtnToPrintPage.setLayoutX(-6);
        BtnToPrintPage.setLayoutY(267);
        BtnToPrintPage.setId("BtnsOnAnchThird");
        BtnToPrintPage.setStyle("-fx-text-fill: whitesmoke");
        BtnToPrintPage.setOnAction(e->{
            pnlPrint.toFront();
        });

        //creating BtnToPrintPage icon settling
        Image ImageBtnToPrintPage=new Image("images/printer.png");
        ImageView imageViewBtnToPrintPage= new ImageView(ImageBtnToPrintPage);
        imageViewBtnToPrintPage.setFitWidth(45);
        imageViewBtnToPrintPage.setFitHeight(40);
        imageViewBtnToPrintPage.setPickOnBounds(true);
        imageViewBtnToPrintPage.setPreserveRatio(true);
        BtnToPrintPage.setGraphic(imageViewBtnToPrintPage);

        //creating button to save page
        BtnToSavePage = new Button("       Save");
        BtnToSavePage.setPrefSize(164,56);
        BtnToSavePage.setLayoutX(-11);
        BtnToSavePage.setLayoutY(372);
        BtnToSavePage.setId("BtnsOnAnchThird");
        BtnToSavePage.setStyle("-fx-text-fill: whitesmoke");
        BtnToSavePage.setOnAction(e->{
            pnlSave.toFront();
        });

        //creating button to save this to textfile
        BtnToSaveToTextFile=new Button("Save To TextFile");
        BtnToSaveToTextFile.setPrefSize(164,56);
        BtnToSaveToTextFile.setLayoutX(400);
        BtnToSaveToTextFile.setLayoutY(225);
        BtnToSaveToTextFile.setOnAction(e->saveTextFileBtnClicked());

        //creating BtnToSavePage icon settling
        Image ImageBtnToSavePage=new Image("images/diskette.png");
        ImageView imageViewBtnToSavePage= new ImageView(ImageBtnToSavePage);
        imageViewBtnToSavePage.setFitWidth(45);
        imageViewBtnToSavePage.setFitHeight(40);
        imageViewBtnToSavePage.setPickOnBounds(true);
        imageViewBtnToSavePage.setPreserveRatio(true);
        BtnToSavePage.setGraphic(imageViewBtnToSavePage);

        anchThird.getChildren().addAll(BtnToAddPage,BtnToSortPage,BtnToPrintPage,BtnToSavePage);

        // setting up the stage
        primaryStage.setTitle("GYM Manager 2020");
        primaryStage.setWidth(845);
        primaryStage.setHeight(545);

        //creating main anchorpane
        AnchorPane anchMain=new AnchorPane();
        anchMain.setId("anchMain");
        anchMain.setMaxWidth(845);
        anchMain.setMaxHeight(545);

        //creating secondary anch pane
        AnchorPane anchSecond=new AnchorPane();
        anchSecond.setId("anchSecond");
        anchSecond.setMaxWidth(761);
        anchSecond.setMaxHeight(501);
        anchSecond.setLayoutX(3.0);
        DropShadow shadowanchSecond=new DropShadow();
        anchSecond.setEffect(shadowanchSecond);

        // creating add pane
        pnlAdd =new Pane();
        pnlAdd.prefHeight(539);
        pnlAdd.prefWidth(838);
        pnlAdd.setStyle("-fx-background-color: #65DEF1;");

        // add member button in add panel
        BtnAddMember= new Button("Add member");
        BtnAddMember.setLayoutX(574);
        BtnAddMember.setLayoutY(420);
        BtnAddMember.setPrefSize(150,30);
        BtnAddMember.setId("BtnsOnAnchThird");
        BtnAddMember.setOnAction(e ->{
            memberLastOrNot="Yes";
            addButtonClicked();
        });

//setting up images to add member panel
        Image pnl_add=new Image("images/pnlAddImg.jpg");
        ImageView pnlAddImgView= new ImageView(pnl_add);
        pnlAddImgView.setFitHeight(545);
        pnlAddImgView.setFitWidth(843);
        pnlAddImgView.setLayoutX(-3.0);
        pnlAddImgView.setLayoutY(-2.0);

//setting up the the add  member panel grid pane
        grdAddpnl1= new GridPane();
        grdAddpnl1.setHgap(10);
        grdAddpnl1.setVgap(30);
        grdAddpnl1.setLayoutX(250);
        grdAddpnl1.setLayoutY(49);

        // hbox for sets the date
       HBox hboxForDate= new HBox();
       hboxForDate.setPadding(new Insets(10,10,10,10));
       GridPane.setConstraints(hboxForDate,0,4);

       //Vbox for sets the error messages
         VboxForMessages= new VBox();
         ErrorM1=new Label();
         ErrorM1.setId("ErrorMessages");
         ErrorM2=new Label();
         ErrorM2.setId("ErrorMessages");
         ErrorM3=new Label();
         ErrorM3.setId("ErrorMessages");
         ErrorM4=new Label();
         ErrorM4.setId("ErrorMessages");
         ErrorM5 =new Label();
         ErrorM5.setId("ErrorMessages");

        //creating text fields and label for the add member panel
        lblAddPnlFirstName=new Label("First Name:");
        lblAddPnlFirstName.setId("Label");
        txtFldAddPnlFirstName= new TextField();
        txtFldAddPnlFirstName.setId("text-field");
        txtFldAddPnlFirstName.setPrefSize(150,30);
        GridPane.setConstraints(txtFldAddPnlFirstName,1,0);
        GridPane.setConstraints(lblAddPnlFirstName,0,0);

        lblAddPnlLastName=new Label("Last Name:");
        txtFldAddPnlLastName= new TextField();
        txtFldAddPnlLastName.setPrefSize(150,30);
        lblAddPnlLastName.setId("Label");
        txtFldAddPnlLastName.setId("txtFldAddPnlLastName");
        GridPane.setConstraints(txtFldAddPnlLastName,1,1);
        GridPane.setConstraints(lblAddPnlLastName,0,1);
        lblAddPnlStartDate=new Label("Start Date: ");

        txtFldAddPnlStartDate= new TextField();
        txtFldAddPnlStartMonth= new TextField();
        txtFldAddPnlStartYear= new TextField();
        txtFldAddPnlStartDate.setMaxWidth(50);
        txtFldAddPnlStartMonth.setMaxWidth(50);
        txtFldAddPnlStartYear.setMaxWidth(50);
        GridPane.setConstraints(lblAddPnlStartDate,0,3);
        GridPane.setConstraints(txtFldAddPnlStartDate,1,3);
        GridPane.setConstraints(txtFldAddPnlStartMonth,2,3);
        GridPane.setConstraints(txtFldAddPnlStartYear,3,3);

        lblAddPnlStartDate.setId("Label");
        txtFldAddPnlStartDate.setId("txtFldAddPnlStartDate");
        hboxForDate.getChildren().addAll(lblAddPnlStartDate,txtFldAddPnlStartDate,txtFldAddPnlStartMonth,txtFldAddPnlStartYear);
        VboxForMessages.getChildren().addAll(ErrorM1,ErrorM2,ErrorM3,ErrorM4,ErrorM5);
        GridPane.setConstraints(VboxForMessages,0,7);


        lblAddPnlMemberType=new Label("Membership Type");
        comboAddPnlMemberType= new ComboBox();
        comboAddPnlMemberType.getItems().addAll("Default Membership","Student Membership","Over60 Membership");
        comboAddPnlMemberType.setValue("Default Membership");
        comboAddPnlMemberType.setPrefSize(200,10);
        comboAddPnlMemberType.setId("BtnsOnAnchThird");
        lblAddPnlMemberType.setId("Label");
        GridPane.setConstraints(lblAddPnlMemberType,0,2);
        GridPane.setConstraints(comboAddPnlMemberType,1,2);

        //setting up the functionality for combobox selections
        comboAddPnlMemberType.setOnAction(e->{
            if(comboAddPnlMemberType.getValue()=="Student Membership"){
                grdAddpnl2.getChildren().addAll(txtFldSchoolName,lblSchoolName);
                //txtFldAge,lblAge,txtFldSchoolName,lblSchoolName
                grdAddpnl2.getChildren().removeAll(txtFldAge,lblAge);
            }else if(comboAddPnlMemberType.getValue()=="Over60 Membership"){
                grdAddpnl2.getChildren().addAll(txtFldAge,lblAge);
                grdAddpnl2.getChildren().removeAll(txtFldSchoolName,lblSchoolName);
            }else{
                grdAddpnl2.getChildren().removeAll(txtFldSchoolName,lblSchoolName,txtFldAge,lblAge);
            }
        });

        //setting up the the add  member panel grid pane
        grdAddpnl2= new GridPane();
        grdAddpnl2.setHgap(10);
        grdAddpnl2.setVgap(30);
        grdAddpnl2.setLayoutX(250);
        grdAddpnl2.setLayoutY(320);
        grdAddpnl2.setPrefHeight(500);

        //creating text fields and label for the add member panel
        lblAge=new Label("Age:");
        lblAge.setId("Label");
        txtFldAge= new TextField();
        txtFldAge.setId("txtFldAge");
        txtFldAge.setPrefSize(70,30);
        GridPane.setConstraints(txtFldAge,1,1);
        GridPane.setConstraints(lblAge,0,1);

        lblSchoolName=new Label("School Name:");
        txtFldSchoolName= new TextField();
        txtFldAddPnlLastName.setPrefSize(276,30);
        lblSchoolName.setId("Label");
        txtFldSchoolName.setId("txtFldSchoolName");
        GridPane.setConstraints(txtFldSchoolName,1,1);
        GridPane.setConstraints(lblSchoolName,0,1);


        //creating home page pane
        pnlHome =new Pane();
        pnlHome.prefHeight(539);
        pnlHome.prefWidth(838);
        pnlHome.setStyle("-fx-background-color: #65DEF1;");


        //setting up images to  home pane
        Image pnl_home=new Image("images/homePage.jpg");
        ImageView pnlHomeImgView= new ImageView(pnl_home);
        pnlHomeImgView.setFitHeight(552);
        pnlHomeImgView.setFitWidth(850);

        //creating print page pane
        pnlPrint =new Pane();
        pnlPrint.prefHeight(539);
        pnlPrint.prefWidth(838);
        pnlPrint.setStyle("-fx-background-color: #65DEF1;");

        //creating combo box in print pane
        comboPrintMemberType= new ComboBox();
        comboPrintMemberType.getItems().addAll("Default Membership","Student Membership","Over60 Membership");
        comboPrintMemberType.setValue("");
        comboPrintMemberType.setPrefSize(276,30);
        comboPrintMemberType.setId("BtnsOnAnchThird");
        comboPrintMemberType.setLayoutX(280);
        comboPrintMemberType.setLayoutY(50);

        //Creating scroll pane to set  data table in print pane
        ScrollPrintPaneTable= new ScrollPane();
        ScrollPrintPaneTable.setLayoutX(235);
        ScrollPrintPaneTable.setLayoutY(120);
        ScrollPrintPaneTable.setPrefWidth(531);
        ScrollPrintPaneTable.setPrefHeight(361);

        //setting up images to  print pane
        Image pnl_print=new Image("images/printPage.jpg");
        ImageView pnlPrintImgView= new ImageView(pnl_print);
        pnlPrintImgView.setFitHeight(545);
        pnlPrintImgView.setFitWidth(843);
        pnlPrintImgView.setLayoutX(-3.0);
        pnlPrintImgView.setLayoutY(-2.0);

        //creating sort page pane
        pnlSort =new Pane();
        pnlSort.prefHeight(539);
        pnlSort.prefWidth(838);
        pnlSort.setStyle("-fx-background-color: #65DEF1;");


        //setting up images to  sort pane
        Image pnl_sort=new Image("images/sortPage.jpg");
        ImageView pnlSortImgView= new ImageView(pnl_sort);
        pnlSortImgView.setFitHeight(545);
        pnlSortImgView.setFitWidth(843);
        pnlSortImgView.setLayoutX(-3.0);
        pnlSortImgView.setLayoutY(-2.0);

        //remove button in sort
        BtnRemoveSortPane =new Button("      Remove");
        BtnRemoveSortPane.setPrefSize(158,44);
        BtnRemoveSortPane.setLayoutX(600);
        BtnRemoveSortPane.setLayoutY(50);
        BtnRemoveSortPane.setId("BtnsOnAnchThird");
        BtnRemoveSortPane.setStyle("-fx-text-fill: white");
        BtnRemoveSortPane.setOnAction(e->deleteBtnClicked());

        //remove button icon settling
        Image ImageBtnRemoveSortPane=new Image("images/deletes.png");
        ImageView imageViewBtnRemoveSortPane= new ImageView(ImageBtnRemoveSortPane);
        imageViewBtnRemoveSortPane.setFitWidth(44);
        imageViewBtnRemoveSortPane.setFitHeight(30);
        imageViewBtnRemoveSortPane.setPickOnBounds(true);
        imageViewBtnRemoveSortPane.setPreserveRatio(true);
        BtnRemoveSortPane.setGraphic(imageViewBtnRemoveSortPane);

        //button to sort the table
        BtnSortPageSort =new Button();
        BtnSortPageSort.setPrefSize(73,52);
        BtnSortPageSort.setLayoutX(199);
        BtnSortPageSort.setLayoutY(48);
        BtnSortPageSort.setId("BtnSortPageSort");

        comboSortMemberType= new ComboBox();
        comboSortMemberType.getItems().addAll("Default Membership","Student Membership","Over60 Membership");
        comboSortMemberType.setValue("");
        comboSortMemberType.setPrefSize(276,30);
        comboSortMemberType.setId("BtnsOnAnchThird");
        comboSortMemberType.setLayoutX(280);
        comboSortMemberType.setLayoutY(48);
        comboSortMemberType.setValue("Selects The Table");

        //sort button icon setting up
        Image ImageBtnSortSortPane=new Image("images/sort-az.png");
        ImageView imageViewBtnSortSortPane= new ImageView(ImageBtnSortSortPane);
        imageViewBtnSortSortPane.setFitWidth(44);
        imageViewBtnSortSortPane.setFitHeight(38);
        imageViewBtnSortSortPane.setPickOnBounds(true);
        imageViewBtnSortSortPane.setPreserveRatio(true);
        BtnSortPageSort.setGraphic(imageViewBtnSortSortPane);

        //Creating scroll pane to data table
        ScrollPaneTable= new ScrollPane();
        ScrollPaneTable.setLayoutX(235);
        ScrollPaneTable.setLayoutY(120);
        ScrollPaneTable.setPrefWidth(531);
        ScrollPaneTable.setPrefHeight(361);

        //creating table for the default members
        defaultTable= new TableView<>();
        defaultTable.setPrefHeight(553);
        defaultTable.setPrefWidth(521);

        //setting up the columns for the default table
        TableColumn<DefaultClass,String> colFirstName= new TableColumn("First Name");
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
        colFirstName.setPrefWidth(100);

        TableColumn<DefaultClass,String> colLastName= new TableColumn("Last Name");
        colLastName.setCellValueFactory(new PropertyValueFactory<>("LastName"));
        colLastName.setPrefWidth(100);

        TableColumn<DefaultClass,String> colMembershipType= new TableColumn("Membership Type");
        colMembershipType.setCellValueFactory(new PropertyValueFactory<>("MembershipType"));
        colMembershipType.setPrefWidth(150);

        TableColumn<DefaultClass,String> colDate= new TableColumn("Date");
        colDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
        colDate.setPrefWidth(100);

        defaultTable.getColumns().addAll(colFirstName, colLastName,colMembershipType,colDate);
/////////////////////////////////////////////////////////////////////////////////////////////////////////////

        //creating tableview for the student members
        StudentTable= new TableView<>();
        StudentTable.setPrefHeight(553);
        StudentTable.setPrefWidth(521);

        //creating cloumns for he student table
        TableColumn<StudentClass,String> colStudentFirstName= new TableColumn("First Name");
        colStudentFirstName.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
        colStudentFirstName.setPrefWidth(100);

        TableColumn<StudentClass,String> colStudentLastName= new TableColumn("Last Name");
        colStudentLastName.setCellValueFactory(new PropertyValueFactory<>("LastName"));
        colStudentLastName.setPrefWidth(100);

        TableColumn<StudentClass,String> colStudentMembershipType= new TableColumn("Membership Type");
        colStudentMembershipType.setCellValueFactory(new PropertyValueFactory<>("MembershipType"));
        colStudentMembershipType.setPrefWidth(100);

        TableColumn<StudentClass,String> colSchoolName= new TableColumn("School Name");
        colSchoolName.setCellValueFactory(new PropertyValueFactory<>("SchoolName"));
        colSchoolName.setPrefWidth(100);

        TableColumn<DefaultClass,String> colStudentDate= new TableColumn("Date");
        colStudentDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
        colStudentDate.setPrefWidth(100);

        StudentTable.getColumns().addAll(colStudentFirstName, colStudentLastName,colStudentMembershipType,colSchoolName,colStudentDate);
//////// /////////////// /////////// //////////////// //////////////// //////////////// ////////////// //////////////// /////////////////////////////////////

        //creating tableview for the over60 members
        Over60Table= new TableView<>();
        Over60Table.setPrefHeight(553);
        Over60Table.setPrefWidth(521);

        //creating table column for the over60 table
        TableColumn<Over60Class,String> colOver60FirstName= new TableColumn("First Name");
        colOver60FirstName.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
        colOver60FirstName.setPrefWidth(100);

        TableColumn<Over60Class,String> colOver60LastName= new TableColumn("Last Name");
        colOver60LastName.setCellValueFactory(new PropertyValueFactory<>("LastName"));
        colOver60LastName.setPrefWidth(100);

        TableColumn<Over60Class,String> colOver60MembershipType= new TableColumn("Membership Type");
        colOver60MembershipType.setCellValueFactory(new PropertyValueFactory<>("MembershipType"));
        colOver60MembershipType.setPrefWidth(100);

        TableColumn<Over60Class,String> colOver60Age= new TableColumn("Age ");
        colOver60Age.setCellValueFactory(new PropertyValueFactory<>("age"));
        colOver60Age.setPrefWidth(100);

        TableColumn<Over60Class,String> colOver60Date= new TableColumn("Date");
        colOver60Date.setCellValueFactory(new PropertyValueFactory<>("Date"));
        colOver60Date.setPrefWidth(100);

        Over60Table.getColumns().addAll(colOver60FirstName,colOver60LastName,colOver60MembershipType,colOver60Age,colOver60Date);

////////// //////////// ////////////// /////////////// /////////////// ////////////// ///////////// ///////////// //////////// ////////
        //creating save page
        pnlSave =new Pane();
        pnlSave.prefHeight(539);
        pnlSave.prefWidth(838);
        pnlSave.setStyle("-fx-background-color: #65DEF1;");

        //creating combo box in print pane
        comboSaveMemberType= new ComboBox();
        comboSaveMemberType.getItems().addAll("Default Membership","Student Membership","Over60 Membership");
        comboSaveMemberType.setValue("");
        comboSaveMemberType.setPrefSize(300,30);
        comboSaveMemberType.setId("BtnsOnAnchThird");
        comboSaveMemberType.setLayoutX(280);
        comboSaveMemberType.setLayoutY(48);
        comboSaveMemberType.setValue("Select The Table You Want To Save");

        comboSaveMemberType.setOnAction(e->{
            if(comboPrintMemberType.getValue()=="Default Membership") {
                ScrollPrintPaneTable.setContent(defaultTable);
            }else if(comboPrintMemberType.getValue()=="Student Membership"){
                ScrollPrintPaneTable.setContent(StudentTable);
            }else if(comboPrintMemberType.getValue()=="Over60 Membership"){
                ScrollPrintPaneTable.setContent(Over60Table);
            }
        });

        //setting up images to  sort pane
        Image pnl_Save=new Image("images/sortPage.jpg");
        ImageView pnlSaveImgView= new ImageView(pnl_Save);
        pnlSaveImgView.setFitHeight(545);
        pnlSaveImgView.setFitWidth(843);
        pnlSaveImgView.setLayoutX(-3.0);
        pnlSaveImgView.setLayoutY(-2.0);

        grdAddpnl1.getChildren().addAll(txtFldAddPnlFirstName,lblAddPnlFirstName,lblAddPnlLastName,txtFldAddPnlLastName,lblAddPnlMemberType,comboAddPnlMemberType,hboxForDate,VboxForMessages);
        grdAddpnl2.getChildren().addAll();

        pnlSave.getChildren().addAll(pnlSaveImgView,BtnToSaveToTextFile,comboSaveMemberType);
        pnlSort.getChildren().addAll(pnlSortImgView,BtnRemoveSortPane,ScrollPaneTable,comboSortMemberType);
        pnlPrint.getChildren().addAll(pnlPrintImgView,comboPrintMemberType,ScrollPrintPaneTable);
        pnlHome.getChildren().addAll(pnlHomeImgView);
        pnlAdd.getChildren().addAll(pnlAddImgView,grdAddpnl1,grdAddpnl2,BtnAddMember);
        anchSecond.getChildren().addAll(pnlSave,pnlSort,pnlPrint,pnlAdd,pnlHome);
        anchMain.getChildren().addAll(anchSecond,anchThird);

        Scene firstScene= new Scene(anchMain);
        firstScene.getStylesheets().add("stylesheets/Inteface.css");
        primaryStage.setScene(firstScene);
        primaryStage.show();
        primaryStage.setResizable(false);

        comboSortMemberType.setOnAction(e->{
            if(comboSortMemberType.getValue()=="Default Membership") {
                ScrollPaneTable.setContent(defaultTable);
            }else if(comboSortMemberType.getValue()=="Student Membership"){
                ScrollPaneTable.setContent(StudentTable);
            }else if(comboSortMemberType.getValue()=="Over60 Membership"){
                ScrollPaneTable.setContent(Over60Table);
            }
        });
//
        //printing pane button action to choose the table view
        comboPrintMemberType.setOnAction(e->{
            if(comboPrintMemberType.getValue()=="Default Membership") {
                ScrollPrintPaneTable.setContent(defaultTable);
            }else if(comboPrintMemberType.getValue()=="Student Membership"){
                ScrollPrintPaneTable.setContent(StudentTable);
            }else if(comboPrintMemberType.getValue()=="Over60 Membership"){
                ScrollPrintPaneTable.setContent(Over60Table);
            }
        });
    }

    public static void main(String[] args) {
            launch(args);

    }

    //creating save button functionality in save pane
    private void saveTextFileBtnClicked() {

        List<List<String>> arrListStudent = new ArrayList<>();
        List<List<String>> arrListDefault = new ArrayList<>();
        List<List<String>> arrListOver60 = new ArrayList<>();

        if (comboSaveMemberType.getValue() == "Default Membership") {
            File fileName = new File("Defaultmembers.txt");
            try {
                FileWriter fw = new FileWriter(fileName);
                Writer output= new BufferedWriter(fw);
            for (int i = 0; i < defaultTable.getItems().size(); i++) {
                defaultMember = (DefaultClass) defaultTable.getItems().get(i);
                arrListDefault.add(new ArrayList<>());
                arrListDefault.get(i).add("Default member Record"+i+"\nFirst Name :" + defaultMember.getFirstName());
                arrListDefault.get(i).add("Last Name: " + defaultMember.getLastName());
                arrListDefault.get(i).add("Date: " + defaultMember.getDate() + "\n");
                output.write(arrListDefault.get(i).toString());
            }
            for (int i = 0; i < arrListDefault.size(); i++) {
                for (int j = 0; j < arrListDefault.get(i).size(); j++) {
                }
            }
                output.close();
            }catch(Exception e ){
                System.out.println("cannot create file");
            }
        }


        if (comboSaveMemberType.getValue() == "Student Membership") {

            File fileName = new File("Studentmembers.txt");
            try {
                FileWriter fw = new FileWriter(fileName);
                Writer output= new BufferedWriter(fw);

                for (int i = 0; i < StudentTable.getItems().size(); i++) {
                    studentMember = (StudentClass) StudentTable.getItems().get(i);
                    arrListStudent.add(new ArrayList<>());
                    arrListStudent.get(i).add("Student members Record"+i+"\nFirst Name: " + studentMember.getFirstName());
                    arrListStudent.get(i).add("Last Name: " + studentMember.getLastName());
                    arrListStudent.get(i).add("School Name: " + studentMember.getSchoolName());
                    arrListStudent.get(i).add("Date: " + studentMember.getDate() + "\n");
                    output.write(arrListStudent.get(i).toString());
                }

                for (int i = 0; i < arrListStudent.size(); i++) {
                    for (int j = 0; j < arrListStudent.get(i).size(); j++) {

                    }
                }
                output.close();
            }catch(Exception e ){
                System.out.println("cannot create file");
            }
        }

        if (comboSaveMemberType.getValue() == "Over60 Membership") {

            File fileName = new File("Over60members.txt");
            try {
                FileWriter fw = new FileWriter(fileName);
                Writer output= new BufferedWriter(fw);
            for (int i = 0; i < Over60Table.getItems().size(); i++) {
                over60Member= (Over60Class) Over60Table.getItems().get(i);
                arrListOver60.add(new ArrayList<>());
                arrListOver60.get(i).add("Over60 members Records+\nFirst Name: " + over60Member.getFirstName());
                arrListOver60.get(i).add("Last Name: " + over60Member.getLastName());
                arrListOver60.get(i).add("Age: " + over60Member.getAge());
                arrListOver60.get(i).add("Date: " + over60Member.getDate() + "\n");
                output.write(arrListOver60.get(i).toString());
            }

            for (int i = 0; i < arrListOver60.size(); i++) {
                for (int j = 0; j < arrListOver60.get(i).size(); j++) {
                    System.out.println(arrListOver60.get(i).get(j));
                }
            }
                output.close();
            }catch(Exception e ){
                System.out.println("cannot create file");
            }
        }
    }
////// //////// ///////////  /////////////  /////////////  ////////////////  ///////////////  /////////
    //add button functionality
    public void  addButtonClicked() {
        defaultMember = new DefaultClass();
        studentMember = new StudentClass();
        over60Member = new Over60Class();
        Date d = new Date();

        if (comboAddPnlMemberType.getValue() == "Default Membership") {
            checkdef=true;
            if (isNumber(txtFldAddPnlStartDate.getText()) && isNumber(txtFldAddPnlStartMonth.getText()) && isNumber(txtFldAddPnlStartYear.getText())) {
                //&& isString(txtFldAddPnlFirstName.getText()) && isString(txtFldAddPnlLastName.getText()

                if (isValidDay(txtFldAddPnlStartDate.getText()) && isValidMonth(txtFldAddPnlStartMonth.getText()) && isValidYear(txtFldAddPnlStartYear.getText())) {
                    d.setDay(Integer.parseInt(txtFldAddPnlStartDate.getText()));
                    d.setMonth(Integer.parseInt(txtFldAddPnlStartMonth.getText()));
                    d.setYear(Integer.parseInt(txtFldAddPnlStartYear.getText()));
                    txtFldAddPnlStartYear.clear();
                    txtFldAddPnlStartDate.clear();
                    txtFldAddPnlStartMonth.clear();
                    defaultMember.setDate(d.toString());
                    ErrorM1.setText("");
                }else{
                    ErrorM1.setText("Please check the date");
                    checkdef=false;
                }
                defaultMember.setMembershipType((String) comboAddPnlMemberType.getValue());
            } else {
                ErrorM1.setText("Please check the date");
                checkdef=false;
            }
            if(!isString(txtFldAddPnlFirstName.getText())){
                ErrorM2.setText("Please Enter valid First Name ");
                checkdef=false;
            }else{
                ErrorM2.setText("");
            }
            if(!isString(txtFldAddPnlLastName.getText())){
                ErrorM3.setText("Please Enter valid Last Name ");
                checkdef=false;
            }else{
                ErrorM3.setText("");
            }
            if(checkdef==true) {

                    allCollection = db.getCollection("AllMember");
                    obj1 = new BasicDBObject();
                    DBCursor cursor1 = collection1.find();
                    DBCursor cursor2 = allCollection.find();
                    setDatabaseValidation(allCollection, collection1, cursor1, cursor2, firstMember, obj1);
                    obj1.put("First Name", txtFldAddPnlFirstName.getText());
                    obj1.put("Last Name", txtFldAddPnlLastName.getText());
                    obj1.put("Start Date", d.toString());
                    obj1.put("Membership Type", "Default Membership");
                    obj1.put("Last", "Yes");
                    collection1.insert(obj1);
                    allCollection.insert(obj1);
//                allCollections.insert(obj1);

                defaultMember.setFirstName(String.valueOf(txtFldAddPnlFirstName.getText()));
                defaultMember.setLastName(String.valueOf(txtFldAddPnlLastName.getText()));
                defaultTable.getItems().add(defaultMember);
                System.out.println(collection1);
                System.out.println(memberLastOrNot);
                txtFldAddPnlFirstName.clear();
                txtFldAddPnlLastName.clear();
                ErrorM2.setText("");
                ErrorM3.setText("");

                //show the message about succesfully adding member
                Alert alertSuccess= new Alert(Alert.AlertType.INFORMATION);
                alertSuccess.setTitle("Alert!");
                alertSuccess.setHeaderText(" New Member Successfully Added");
                alertSuccess.show();
            }
/////////// ////////////// //////////////// ///////////////// ////////////////// ////////// /////////////
        }if (comboAddPnlMemberType.getValue() == "Student Membership") {
            checkstud=true;
            if (isNumber(txtFldAddPnlStartDate.getText()) && isNumber(txtFldAddPnlStartMonth.getText()) && isNumber(txtFldAddPnlStartYear.getText())) {
                //&& isString(txtFldAddPnlFirstName.getText()) && isString(txtFldAddPnlLastName.getText()
                if (isValidDay(txtFldAddPnlStartDate.getText()) && isValidMonth(txtFldAddPnlStartMonth.getText()) && isValidYear(txtFldAddPnlStartYear.getText())) {
                    d.setDay(Integer.parseInt(txtFldAddPnlStartDate.getText()));
                    d.setMonth(Integer.parseInt(txtFldAddPnlStartMonth.getText()));
                    d.setYear(Integer.parseInt(txtFldAddPnlStartYear.getText()));
                    studentMember.setMembershipType((String) comboAddPnlMemberType.getValue());
                    studentMember.setDate(d.toString());
                    txtFldAddPnlStartYear.clear();
                    txtFldAddPnlStartDate.clear();
                    txtFldAddPnlStartMonth.clear();
                    ErrorM1.setText("");
                    checkstud=true;
                }else{
                    ErrorM1.setText("Please check the date");
                    checkstud=false;
                }
            }else {
                ErrorM1.setText("Please check the date");
                checkstud=false;
            }
            if(!isString(txtFldAddPnlFirstName.getText())){
                ErrorM2.setText("Please Enter valid First Name ");
                checkstud=false;
            }else{
                ErrorM2.setText("");
            }
            if(!isString(txtFldAddPnlLastName.getText())){
                ErrorM3.setText("Please Enter valid Last Name ");
                checkstud=false;
            }else{
                ErrorM3.setText("");
            }
            if(!isString(txtFldSchoolName.getText())){
                ErrorM4.setText("Please Recheck Your School Name");
                checkstud=false;
            }else{
                ErrorM4.setText("");
            }
            if(checkstud==true){

                collection2=db.getCollection("StudentMember");
                obj2= new BasicDBObject();
                DBCursor cursor1 = collection2.find();
                DBCursor cursor2 = allCollection.find();
                setDatabaseValidation(allCollection, collection2, cursor1, cursor2, firstMember, obj2);
                obj2.put("First Name",txtFldAddPnlFirstName.getText());
                obj2.put("Last Name",txtFldAddPnlLastName.getText());
                obj2.put("Start Date",d.toString());
                obj2.put("School Name",txtFldSchoolName.getText());
                obj2.put("Membership Type","Student Membership");
                obj2.put("Last","Yes");
                collection2.insert(obj2);
                allCollection.insert(obj2);

                studentMember.setFirstName(txtFldAddPnlFirstName.getText());
                studentMember.setLastName(txtFldAddPnlLastName.getText());
                studentMember.setSchoolName(txtFldSchoolName.getText());
                StudentTable.getItems().add(studentMember);
                ErrorM2.setText("");
                ErrorM3.setText("");
                ErrorM4.setText("");

                txtFldAddPnlFirstName.clear();
                txtFldAddPnlLastName.clear();
                txtFldSchoolName.clear();
            }
//////// ////////// ///////////// ///////////// ///////////// ///////////// ///////////// ////////////// //////////////
        }if (comboAddPnlMemberType.getValue() == "Over60 Membership") {
            checkOver60 = true;
            if (isNumber(txtFldAddPnlStartDate.getText()) && isNumber(txtFldAddPnlStartMonth.getText()) && isNumber(txtFldAddPnlStartYear.getText())) {

                if (isValidDay(txtFldAddPnlStartDate.getText()) && isValidMonth(txtFldAddPnlStartMonth.getText()) && isValidYear(txtFldAddPnlStartYear.getText())) {
                    d.setDay(Integer.parseInt(txtFldAddPnlStartDate.getText()));
                    d.setMonth(Integer.parseInt(txtFldAddPnlStartMonth.getText()));
                    d.setYear(Integer.parseInt(txtFldAddPnlStartYear.getText()));
                    txtFldAddPnlStartYear.clear();
                    txtFldAddPnlStartDate.clear();
                    txtFldAddPnlStartMonth.clear();
                    over60Member.setDate(d.toString());
                    ErrorM1.setText("");
                    checkOver60 = true;
                } else {
                    ErrorM1.setText("Please check the date");
                    checkOver60 = false;
                }
            } else {
                ErrorM1.setText("Please check the date");
                checkOver60 = false;
            }
            if (!isString(txtFldAddPnlFirstName.getText())) {
                ErrorM2.setText("Please Enter valid First Name ");
                checkOver60 = false;
            } else {
                ErrorM2.setText("");
            }
            if (!isString(txtFldAddPnlLastName.getText())) {
                ErrorM3.setText("Please Enter valid Last Name ");
                checkOver60 = false;
            } else {
                ErrorM3.setText("");
            }
            if (!isNumber(txtFldAge.getText())) {
                ErrorM5.setText("Please Enter Numeric Values in Age Field");
                checkOver60 = false;
            } else {
                ErrorM5.setText("");
            }

            if (checkOver60 == true) {
                collection3=db.getCollection("Over60Member");
                obj3= new BasicDBObject();
                DBCursor cursor1 = collection2.find();
                DBCursor cursor2 = allCollection.find();
                setDatabaseValidation(allCollection, collection3, cursor1, cursor2, firstMember, obj3);

                obj3.put("First Name",txtFldAddPnlFirstName.getText());
                obj3.put("Last Name",txtFldAddPnlLastName.getText());
                obj3.put("Start Date",d.toString());
                obj3.put("Age",txtFldAge.getText());
                obj3.put("Membership Type","Over60 Membership");
                obj3.put("Last","Yes");
                collection3.insert(obj3);
                allCollection.insert(obj3);

                over60Member.setFirstName(txtFldAddPnlFirstName.getText());
                over60Member.setLastName(txtFldAddPnlLastName.getText());
                over60Member.setAge(Integer.parseInt(txtFldAge.getText()));
                over60Member.setMembershipType((String) comboAddPnlMemberType.getValue());
                Over60Table.getItems().add(over60Member);
                txtFldAddPnlFirstName.clear();
                collection1 = db.getCollection("DefaultMember");
                txtFldAddPnlLastName.clear();
                txtFldAge.clear();
                ErrorM2.setText("");
                ErrorM3.setText("");
                ErrorM5.setText("");

            }
        }
    }
//////// //////////// /////////// ///////////// /////////// ////////////// //////////// //////////// /////////// ///
    // validator for thecollections
    public void setDatabaseValidation(
            DBCollection TableForAllMembers,
            DBCollection eachTable,
            DBCursor CursorfindIterable,
            DBCursor CursorfindIterableAll,
            Boolean firstMember,
            BasicDBObject basicObj
    ){
        for (DBObject counter1 : CursorfindIterable){
            firstMember=false;
            memberLastOrNot = (String) counter1.get("Last");
            System.out.println(memberLastOrNot);
            if(memberLastOrNot.equals("Yes")){

                BasicDBObject query = new BasicDBObject();
                query.put("Last",memberLastOrNot);
                BasicDBObject newValue = new BasicDBObject();
                newValue.put("Last","No");
                BasicDBObject updateObject = new BasicDBObject();
                updateObject.put("$set",newValue);
                eachTable.update(query,updateObject);
            }
        }
        for (DBObject counter2 : CursorfindIterableAll){
            firstMember=false;
            memberLastOrNot = (String) counter2.get("Last");
            if(memberLastOrNot.equals("Yes")){
                BasicDBObject query = new BasicDBObject();
                query.put("Last",memberLastOrNot);
                BasicDBObject newValue = new BasicDBObject();
                newValue.put("Last","No");
                BasicDBObject updateObject = new BasicDBObject();
                updateObject.put("$set",newValue);
                TableForAllMembers.update(query,updateObject);
            }
        }
        if(firstMember==true){
            basicObj.put("Member_No","1");
        }else{
            for (DBObject count : CursorfindIterableAll){
                memberID = (String) count.get("Member_No");
                int number = Integer.parseInt(memberID);
                if(number<100){
                    String newID = Integer.toString(Integer.parseInt(memberID)+1);
                    basicObj.put("Member_No", newID);

                }else{
                    Alert noSpace = new Alert(Alert.AlertType.NONE);
                    noSpace.setAlertType(Alert.AlertType.INFORMATION);
                    noSpace.setContentText("Sorry, No space Available");
                    noSpace.showAndWait();
                    break;
                }

            }
        }
    }
    //delete button to delete selected data from table
    public void deleteBtnClicked(){
        if(comboSortMemberType.getValue()=="Default Membership") {
            ObservableList<DefaultClass> defaultMemberSelected, allDefaultMembers;
            allDefaultMembers = defaultTable.getItems();
            defaultMemberSelected = defaultTable.getSelectionModel().getSelectedItems();
            defaultMemberSelected.forEach(allDefaultMembers::remove);

        }else if(comboSortMemberType.getValue()=="Student Membership"){
            ObservableList<StudentClass> studentMemberSelected, allStudentMembers;
            allStudentMembers = StudentTable.getItems();
            studentMemberSelected = StudentTable.getSelectionModel().getSelectedItems();
            studentMemberSelected.forEach(allStudentMembers::remove);
        }
        else if(comboSortMemberType.getValue()=="Over60 Membership"){
            ObservableList<Over60Class> over60MemberSelected, allOver60Members;
            allOver60Members = Over60Table.getItems();
            over60MemberSelected = Over60Table.getSelectionModel().getSelectedItems();
            over60MemberSelected.forEach(allOver60Members::remove);
        }
    }


}
