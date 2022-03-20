package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Scanner;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Main extends Application {
	private MediaRentalManager system = new MediaRentalManager(new ArrayList<>(), new ArrayList<>());
	private String s = new String();
	private Alert error = new Alert(AlertType.ERROR);
	private Alert warning = new Alert(AlertType.WARNING);
	private Alert success = new Alert(Alert.AlertType.INFORMATION);

	private int i = -1;
	private int j = -1;

	@Override
	public void start(Stage primaryStage) {
		try {
			readFromFile();
		} catch (IOException e) {
			error.setContentText(e.getMessage());
			error.show();
		}

		primaryStage.setMaximized(true);
		primaryStage.setResizable(false);
		primaryStage.setTitle("Media Rental Manager");
		primaryStage.getIcons()
				.add(new Image("https://icon-library.com/images/web-development-icon/web-development-icon-16. 	jpg"));
		Scene scene = new Scene(new Pane());
		primaryStage.setScene(scene);
		primaryStage.setOnCloseRequest(e -> {
			print();
		});
		mainInterface(scene);

		primaryStage.show();
	}

	public void mainInterface(Scene scene) {
		ImageView customerIcone = new ImageView("https://img.icons8.com/office/344/gender-neutral-user.png");
		customerIcone.setFitWidth(50);
		customerIcone.setFitHeight(50);
		Button customer = new Button("Customer", customerIcone);
		customer.setStyle("-fx-color:DODGERBLUE;-fx-background-radius: 50");
		customer.setMinWidth(200);
		customer.setMinHeight(75);
		customer.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR, 20));

		ImageView mediaIcone = new ImageView(
				"https://img.icons8.com/external-photo3ideastudio-lineal-color-photo3ideastudio/344/external-media-graphic-photo3ideastudio-lineal-color-photo3ideastudio.png");
		mediaIcone.setFitWidth(50);
		mediaIcone.setFitHeight(50);
		Button media = new Button("Media", mediaIcone);
		media.setStyle("-fx-color:DODGERBLUE;-fx-background-radius: 50");
		media.setMinWidth(200);
		media.setMinHeight(75);
		media.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR, 20));

		ImageView rentIcone = new ImageView(
				"https://img.icons8.com/external-wanicon-lineal-color-wanicon/344/external-rental-shop-and-store-wanicon-lineal-color-wanicon.png");
		rentIcone.setFitWidth(50);
		rentIcone.setFitHeight(50);
		Button rent = new Button("Rent", rentIcone);
		rent.setStyle("-fx-color:DODGERBLUE;-fx-background-radius: 50");
		rent.setMinWidth(200);
		rent.setMinHeight(75);
		rent.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR, 20));

		GridPane mainPane = new GridPane();
		mainPane.addColumn(0, customer, media, rent);
		mainPane.setVgap(30);
		mainPane.setAlignment(Pos.CENTER);

		ImageView design = new ImageView();
		try {
			design = new ImageView(new Image(new FileInputStream("MyDesign.png")));
			design.setFitWidth(800);
			design.setFitHeight(450);
		} catch (FileNotFoundException e) {
			error.setContentText("you should add MyDesign.JPED to project folder.");
			error.show();
		}

		HBox all = new HBox(50, mainPane, design);
		all.setAlignment(Pos.CENTER);
		customer.setOnAction(e -> {
			customerInterface(scene);
		});

		media.setOnAction(e -> {
			mediaInterface(scene);
		});
		rent.setOnAction(e -> {
			rentInterface(scene);
		});
		all.setBackground(new Background(new BackgroundImage(new Image("https://wallpaperaccess.com/full/2591633.jpg"),
				null, null, null, null)));
		scene.setRoot(all);

	}

	public void customerInterface(Scene scene) {

		ImageView addIcone = new ImageView("https://img.icons8.com/color/344/add--v1.png");
		addIcone.setFitWidth(35);
		addIcone.setFitHeight(35);
		Button add = new Button("Add new Customer", addIcone);
		add.setMinWidth(350);
		add.setMinHeight(30);
		add.setStyle(
				"-fx-color:LIGHTSLATEGRAY;-fx-background-radius: 50; -fx-font-family: 'Comic Sans MS';-fx-font-size: 20px;; -fx-font-family: 'Comic Sans MS';-fx-font-size: 20px;");

		ImageView deleteIcone = new ImageView("https://img.icons8.com/plasticine/344/filled-trash.png");
		deleteIcone.setFitWidth(35);
		deleteIcone.setFitHeight(35);
		Button remove = new Button("Delete Customer", deleteIcone);
		remove.setMinWidth(350);
		remove.setMinHeight(30);
		remove.setStyle(
				"-fx-color:LIGHTSLATEGRAY;-fx-background-radius: 50; -fx-font-family: 'Comic Sans MS';-fx-font-size: 20px;");

		ImageView updateIcone = new ImageView("https://img.icons8.com/flat-round/344/loop.png");
		updateIcone.setFitWidth(35);
		updateIcone.setFitHeight(35);
		Button update = new Button("Update Customer information", updateIcone);
		update.setMinWidth(350);
		update.setMinHeight(30);
		update.setStyle(
				"-fx-color:LIGHTSLATEGRAY;-fx-background-radius: 50; -fx-font-family: 'Comic Sans MS';-fx-font-size: 20px;");

		ImageView searchIcone = new ImageView("https://img.icons8.com/fluency/344/search.png");
		searchIcone.setFitWidth(35);
		searchIcone.setFitHeight(35);
		Button search = new Button("Search about Customer by id", searchIcone);
		search.setMinWidth(350);
		search.setMinHeight(30);
		search.setStyle(
				"-fx-color:LIGHTSLATEGRAY;-fx-background-radius: 50; -fx-font-family: 'Comic Sans MS';-fx-font-size: 20px;");

		ImageView returnIcone = new ImageView("https://img.icons8.com/color/344/return.png");
		returnIcone.setFitWidth(35);
		returnIcone.setFitHeight(35);
		Button returnButton = new Button("Return to main page", returnIcone);
		returnButton.setMinWidth(350);
		returnButton.setMinHeight(30);
		returnButton.setStyle(
				"-fx-color:LIGHTSLATEGRAY;-fx-background-radius: 50; -fx-font-family: 'Comic Sans MS';-fx-font-size: 20px;");

		GridPane customerPane = new GridPane();
		customerPane.setAlignment(Pos.CENTER);
		customerPane.addColumn(0, add, remove, update, search, returnButton);
		customerPane.setHgap(30);
		customerPane.setAlignment(Pos.CENTER);
		customerPane.setVgap(30);

		add.setOnAction(e -> {
			addCustomer(scene);
		});

		remove.setOnAction(e -> {
			removeCustomer(scene);
		});

		update.setOnAction(e -> {
			updateCustomerInfo(scene);
		});

		search.setOnAction(e -> {
			search(scene);
		});

		returnButton.setOnAction(e -> {
			mainInterface(scene);
		});

		customerPane.setBackground(new Background(new BackgroundImage(
				new Image("https://wallpaperaccess.com/full/2591633.jpg"), null, null, null, null)));

		scene.setRoot(customerPane);
	}

	public void addCustomer(Scene scene) {
		GridPane addPane = new GridPane();
		addPane.setAlignment(Pos.CENTER);
		addPane.setHgap(50);
		addPane.setVgap(20);

		Label IDLable = new Label("Customer ID:");
		IDLable.setTextFill(Color.WHITE);
		TextField IDText = new TextField();
		addPane.add(IDLable, 0, 0);
		addPane.add(IDText, 1, 0);

		Label nameLable = new Label("Customer Name:");
		nameLable.setTextFill(Color.WHITE);
		TextField nameText = new TextField();
		addPane.add(nameLable, 0, 1);
		addPane.add(nameText, 1, 1);
		nameText.setDisable(true);

		IDText.setOnKeyTyped(e -> {
			nameText.setDisable(false);
		});

		Label addressLable = new Label("Customer Address:");
		addressLable.setTextFill(Color.WHITE);
		TextField addressText = new TextField();
		addPane.add(addressLable, 0, 2);
		addPane.add(addressText, 1, 2);
		addressText.setDisable(true);

		nameText.setOnKeyTyped(e -> {
			addressText.setDisable(false);
		});

		Label mobileLable = new Label("Customer Mobile:");
		mobileLable.setTextFill(Color.WHITE);
		TextField mobileText = new TextField();
		addPane.add(mobileLable, 0, 3);
		addPane.add(mobileText, 1, 3);
		mobileText.setDisable(true);

		addressText.setOnKeyTyped(e -> {
			mobileText.setDisable(false);
		});

		Label plan = new Label("Plan:");
		plan.setTextFill(Color.WHITE);
		RadioButton limited = new RadioButton("LIMITED");
		limited.setTextFill(Color.WHITE);
		RadioButton unlimited = new RadioButton("UNLIMITED");
		unlimited.setTextFill(Color.WHITE);
		ToggleGroup planGroup = new ToggleGroup();
		limited.setToggleGroup(planGroup);
		unlimited.setToggleGroup(planGroup);
		limited.setDisable(true);
		unlimited.setDisable(true);
		HBox choice = new HBox(5, limited, unlimited);
		addPane.add(plan, 0, 4);
		addPane.add(choice, 1, 4);

		mobileText.setOnKeyTyped(e -> {
			limited.setDisable(false);
			unlimited.setDisable(false);
		});

		ImageView addIcone = new ImageView("https://img.icons8.com/color/344/add--v1.png");
		addIcone.setFitWidth(35);
		addIcone.setFitHeight(35);
		Button add = new Button("add", addIcone);
		add.setStyle(
				"-fx-color:lightblue;-fx-background-radius: 50; -fx-font-family: 'Comic Sans MS';-fx-font-size: 20px;");
		add.setDisable(true);

		limited.setOnAction(e -> {
			s = "LIMITED";
			add.setDisable(false);
		});

		unlimited.setOnAction(e -> {
			s = "UNLIMITED";
			add.setDisable(false);
		});

		ImageView backIcone = new ImageView("https://img.icons8.com/color/344/return.png");
		backIcone.setFitWidth(35);
		backIcone.setFitHeight(35);
		Button back = new Button("Back", backIcone);
		back.setStyle(
				"-fx-color:lightblue;-fx-background-radius: 50; -fx-font-family: 'Comic Sans MS';-fx-font-size: 20px;");
		HBox control = new HBox(20, add, back);
		control.setAlignment(Pos.CENTER);

		VBox all = new VBox(30, addPane, control);
		all.setAlignment(Pos.CENTER);
		back.setOnAction(e -> {
			customerInterface(scene);

		});
		add.setOnAction(e -> {
			if (binaryCustomerSearch(IDText.getText()) == -1) {
				system.addCustomer(nameText.getText(), addressText.getText(), s, IDText.getText(),
						mobileText.getText());
				success.setContentText("Added !");
				success.setHeaderText("Success");
				success.show();
				IDText.setText("");
				nameText.setText("");
				addressText.setText("");
				mobileText.setText("");
				limited.setSelected(false);
				unlimited.setSelected(false);
				print();
			} else {
				error.setContentText("This id is already exist in the system !!");
				error.show();
			}
		});

		all.setBackground(new Background(new BackgroundImage(new Image("https://wallpaperaccess.com/full/2591633.jpg"),
				null, null, null, null)));
		scene.setRoot(all);
	}

	private int binaryCustomerSearch(String key) {
		ArrayList<Customer> customers = system.getCustomers();
		int l = 0, h = customers.size() - 1;
		while (l <= h) {
			int mid = (l + h) / 2;
			if (customers.get(mid).getID().compareToIgnoreCase(key) > 0)
				h = mid - 1;
			else if (customers.get(mid).getID().compareToIgnoreCase(key) < 0)
				l = mid + 1;
			else
				return mid;
		}

		return -1;
	}

	public void removeCustomer(Scene scene) {
		GridPane removePane = new GridPane();
		removePane.setAlignment(Pos.CENTER);
		removePane.setHgap(50);
		removePane.setVgap(20);

		Label IDLable = new Label("Customer ID:");
		IDLable.setTextFill(Color.WHITE);
		TextField IDText = new TextField();
		removePane.add(IDLable, 0, 0);
		removePane.add(IDText, 1, 0);

		Label nameLable = new Label("Customer Name:");
		nameLable.setTextFill(Color.WHITE);
		TextField nameText = new TextField();
		removePane.add(nameLable, 0, 1);
		removePane.add(nameText, 1, 1);
		nameText.setEditable(false);

		Label addressLable = new Label("Customer Address:");
		addressLable.setTextFill(Color.WHITE);
		TextField addressText = new TextField();
		removePane.add(addressLable, 0, 2);
		removePane.add(addressText, 1, 2);
		addressText.setEditable(false);

		Label mobileLable = new Label("Customer Mobile:");
		mobileLable.setTextFill(Color.WHITE);
		TextField mobileText = new TextField();
		removePane.add(mobileLable, 0, 3);
		removePane.add(mobileText, 1, 3);
		mobileText.setEditable(false);

		Label planLabel = new Label("Plan:");
		planLabel.setTextFill(Color.WHITE);
		TextField planText = new TextField();
		removePane.add(planLabel, 0, 4);
		removePane.add(planText, 1, 4);
		planText.setEditable(false);

		ImageView searchIcone = new ImageView("https://img.icons8.com/fluency/344/search.png");
		searchIcone.setFitWidth(35);
		searchIcone.setFitHeight(35);
		Button search = new Button("Find", searchIcone);
		search.setStyle(
				"-fx-color:lightblue;-fx-background-radius: 50; -fx-font-family: 'Comic Sans MS';-fx-font-size: 20px;");

		ImageView deleteIcone = new ImageView("https://img.icons8.com/plasticine/344/filled-trash.png");
		deleteIcone.setFitWidth(35);
		deleteIcone.setFitHeight(35);
		Button delete = new Button("remove", deleteIcone);
		delete.setDisable(true);
		delete.setStyle(
				"-fx-color:lightblue;-fx-background-radius: 50; -fx-font-family: 'Comic Sans MS';-fx-font-size: 20px;");

		ImageView backIcone = new ImageView("https://img.icons8.com/color/344/return.png");
		backIcone.setFitWidth(35);
		backIcone.setFitHeight(35);
		Button back = new Button("Back", backIcone);
		back.setStyle(
				"-fx-color:lightblue;-fx-background-radius: 50; -fx-font-family: 'Comic Sans MS';-fx-font-size: 20px;");

		HBox control = new HBox(20, search, delete, back);
		control.setAlignment(Pos.CENTER);

		VBox all = new VBox(30, removePane, control);
		all.setAlignment(Pos.CENTER);
		back.setOnAction(e -> {
			customerInterface(scene);

		});

		IDText.setOnAction(e -> {
			int c = binaryCustomerSearch(IDText.getText());
			if (c != -1) {
				nameText.setText(system.getCustomers().get(c).getName());
				addressText.setText(system.getCustomers().get(c).getAddress());
				mobileText.setText(system.getCustomers().get(c).getMobileNum());
				planText.setText(system.getCustomers().get(c).getPlan());
				delete.setDisable(false);
				delete.setOnAction(e1 -> {
					system.getCustomers().remove(c);
					IDText.setText("");
					nameText.setText("");
					addressText.setText("");
					mobileText.setText("");
					planText.setText("");
					success.setContentText("Removed!");
					success.setHeaderText("Success");
					success.show();
					print();
				});
			} else {
				error.setContentText("This ID is NOT in the system!");
				error.show();
				IDText.setText("");
				nameText.setText("");
				addressText.setText("");
				mobileText.setText("");
				planText.setText("");
			}

		});

		search.setOnAction(e -> {
			int c = binaryCustomerSearch(IDText.getText());
			if (c != -1) {
				nameText.setText(system.getCustomers().get(c).getName());
				addressText.setText(system.getCustomers().get(c).getAddress());
				mobileText.setText(system.getCustomers().get(c).getMobileNum());
				planText.setText(system.getCustomers().get(c).getPlan());
				delete.setDisable(false);
				delete.setOnAction(e1 -> {
					system.getCustomers().remove(c);
					IDText.setText("");
					nameText.setText("");
					addressText.setText("");
					mobileText.setText("");
					planText.setText("");
					success.setContentText("Removed!");
					success.setHeaderText("Success");
					success.show();
					print();
				});
			} else {
				error.setContentText("This ID is NOT in the system!");
				error.show();
				IDText.setText("");
				nameText.setText("");
				addressText.setText("");
				mobileText.setText("");
				planText.setText("");
			}

		});

		all.setBackground(new Background(new BackgroundImage(new Image("https://wallpaperaccess.com/full/2591633.jpg"),
				null, null, null, null)));

		scene.setRoot(all);
	}

	public void updateCustomerInfo(Scene scene) {
		GridPane updatePane = new GridPane();
		updatePane.setAlignment(Pos.CENTER);
		updatePane.setHgap(50);
		updatePane.setVgap(20);

		Label IDLable = new Label("Customer ID:");
		IDLable.setTextFill(Color.WHITE);
		TextField IDText = new TextField();
		updatePane.add(IDLable, 0, 0);
		updatePane.add(IDText, 1, 0);

		Label nameLable = new Label("Customer Name:");
		nameLable.setTextFill(Color.WHITE);
		TextField nameText = new TextField();
		updatePane.add(nameLable, 0, 1);
		updatePane.add(nameText, 1, 1);
		nameText.setEditable(false);

		Label addressLable = new Label("Customer Address:");
		addressLable.setTextFill(Color.WHITE);
		TextField addressText = new TextField();
		updatePane.add(addressLable, 0, 2);
		updatePane.add(addressText, 1, 2);
		addressText.setEditable(false);

		Label mobileLable = new Label("Customer Mobile:");
		mobileLable.setTextFill(Color.WHITE);
		TextField mobileText = new TextField();
		updatePane.add(mobileLable, 0, 3);
		updatePane.add(mobileText, 1, 3);
		mobileText.setEditable(false);

		Label planLabel = new Label("Plan:");
		planLabel.setTextFill(Color.WHITE);
		RadioButton limited = new RadioButton("LIMITED");
		limited.setTextFill(Color.WHITE);
		RadioButton unlimited = new RadioButton("UNLIMITED");
		unlimited.setTextFill(Color.WHITE);
		ToggleGroup planGroup = new ToggleGroup();
		limited.setToggleGroup(planGroup);
		unlimited.setToggleGroup(planGroup);
		HBox choice = new HBox(5, limited, unlimited);
		updatePane.add(planLabel, 0, 4);
		updatePane.add(choice, 1, 4);
		limited.setDisable(true);
		unlimited.setDisable(true);

		ImageView searchIcone = new ImageView("https://img.icons8.com/fluency/344/search.png");
		searchIcone.setFitWidth(35);
		searchIcone.setFitHeight(35);
		Button search = new Button("Find", searchIcone);
		search.setStyle(
				"-fx-color:lightblue;-fx-background-radius: 50; -fx-font-family: 'Comic Sans MS';-fx-font-size: 20px;");

		ImageView updateIcone = new ImageView("https://img.icons8.com/flat-round/344/loop.png");
		updateIcone.setFitWidth(35);
		updateIcone.setFitHeight(35);
		Button update = new Button("update", updateIcone);
		update.setDisable(true);
		update.setStyle(
				"-fx-color:lightblue;-fx-background-radius: 50; -fx-font-family: 'Comic Sans MS';-fx-font-size: 20px;");

		ImageView backIcone = new ImageView("https://img.icons8.com/color/344/return.png");
		backIcone.setFitWidth(35);
		backIcone.setFitHeight(35);
		Button back = new Button("Back", backIcone);
		back.setStyle(
				"-fx-color:lightblue;-fx-background-radius: 50; -fx-font-family: 'Comic Sans MS';-fx-font-size: 20px;");

		HBox control = new HBox(20, search, update, back);
		control.setAlignment(Pos.CENTER);

		VBox all = new VBox(30, updatePane, control);
		all.setAlignment(Pos.CENTER);
		back.setOnAction(e -> {
			customerInterface(scene);

		});

		TextField valueText = new TextField();
		Label valueLabel = new Label("Customer value Limited:");
		HBox value = new HBox(50, valueLabel, valueText);
		value.setAlignment(Pos.CENTER);
		valueLabel.setTextFill(Color.WHITE);

		IDText.setOnAction(e -> {
			int c = binaryCustomerSearch(IDText.getText());
			if (c != -1) {
				nameText.setText(system.getCustomers().get(c).getName());
				addressText.setText(system.getCustomers().get(c).getAddress());
				mobileText.setText(system.getCustomers().get(c).getMobileNum());
				valueText.setText(system.getCustomers().get(c).getValue() + "");

				update.setDisable(false);
				nameText.setEditable(true);
				addressText.setEditable(true);
				mobileText.setEditable(true);
				limited.setDisable(false);
				unlimited.setDisable(false);
				if (system.getCustomers().get(c).getPlan().equals("LIMITED")) {
					limited.setSelected(true);
					s = "LIMITED";
					if (all.getChildren().size() < 3)
						all.getChildren().add(1, value);

				} else {
					unlimited.setSelected(true);
					s = "UNLIMITED";
					if (all.getChildren().size() == 3)
						all.getChildren().remove(value);
				}

				limited.setOnAction(e1 -> {
					s = "LIMITED";
					limited.setSelected(true);
					// Label valueLabel = new Label("Customer value Limited:");
					valueLabel.setTextFill(Color.WHITE);

					if (all.getChildren().size() < 3)
						all.getChildren().add(1, value);

				});

				unlimited.setOnAction(e1 -> {
					s = "UNLIMITED";

					if (all.getChildren().size() == 3)
						all.getChildren().remove(value);
					system.getCustomers().get(c).setValue(Integer.MAX_VALUE);
				});

				update.setOnAction(e1 -> {
					system.getCustomers().get(c).setAddress(addressText.getText());
					system.getCustomers().get(c).setMobileNum(mobileText.getText());
					system.getCustomers().get(c).setName(nameText.getText());
					system.getCustomers().get(c).setPlan(s);
					IDText.setText("");
					nameText.setText("");
					addressText.setText("");
					mobileText.setText("");
					limited.setSelected(false);
					unlimited.setSelected(false);
					if (s.equalsIgnoreCase("limited")) {
						Boolean b = true;
						try {
							system.getCustomers().get(c).setValue(Integer.parseInt(valueText.getText()));
						} catch (NumberFormatException ex) {
							error.setContentText("You should enter Integer in value field !!");
							b = false;
							error.show();

						} catch (IllegalArgumentException e2) {
							error.setContentText(e2.getMessage());
							b = false;
							error.show();
						}
						if (all.getChildren().size() == 3)
							all.getChildren().remove(value);

						if (b) {
							success.setContentText("updated!");
							success.setHeaderText("Success!");
							success.show();
						}
					} else {
						success.setContentText("updated!");
						success.setHeaderText("Success!");
						success.show();
					}

					print();
				});
			} else {
				error.setContentText("This ID is NOT in the system!");
				error.show();
				IDText.setText("");
				nameText.setText("");
				addressText.setText("");
				mobileText.setText("");
				limited.setSelected(false);
				unlimited.setSelected(false);
			}

		});

		search.setOnAction(e -> {
			int c = binaryCustomerSearch(IDText.getText());
			if (c != -1) {
				nameText.setText(system.getCustomers().get(c).getName());
				addressText.setText(system.getCustomers().get(c).getAddress());
				mobileText.setText(system.getCustomers().get(c).getMobileNum());
				valueText.setText(system.getCustomers().get(c).getValue() + "");

				update.setDisable(false);
				nameText.setEditable(true);
				addressText.setEditable(true);
				mobileText.setEditable(true);
				limited.setDisable(false);
				unlimited.setDisable(false);
				if (system.getCustomers().get(c).getPlan().equals("LIMITED")) {
					limited.setSelected(true);
					s = "LIMITED";
					if (all.getChildren().size() < 3)
						all.getChildren().add(1, value);

				} else {
					unlimited.setSelected(true);
					s = "UNLIMITED";
					if (all.getChildren().size() == 3)
						all.getChildren().remove(value);
				}

				limited.setOnAction(e1 -> {
					s = "LIMITED";
					limited.setSelected(true);
					// Label valueLabel = new Label("Customer value Limited:");
					valueLabel.setTextFill(Color.WHITE);

					if (all.getChildren().size() < 3)
						all.getChildren().add(1, value);

				});

				unlimited.setOnAction(e1 -> {
					s = "UNLIMITED";

					if (all.getChildren().size() == 3)
						all.getChildren().remove(value);
					system.getCustomers().get(c).setValue(Integer.MAX_VALUE);
				});

				update.setOnAction(e1 -> {
					system.getCustomers().get(c).setAddress(addressText.getText());
					system.getCustomers().get(c).setMobileNum(mobileText.getText());
					system.getCustomers().get(c).setName(nameText.getText());
					system.getCustomers().get(c).setPlan(s);
					IDText.setText("");
					nameText.setText("");
					addressText.setText("");
					mobileText.setText("");
					limited.setSelected(false);
					unlimited.setSelected(false);
					if (s.equalsIgnoreCase("limited")) {
						Boolean b = true;
						try {
							system.getCustomers().get(c).setValue(Integer.parseInt(valueText.getText()));
						} catch (NumberFormatException ex) {
							error.setContentText("You should enter Integer in value field !!");
							b = false;
							error.show();

						} catch (IllegalArgumentException e2) {
							error.setContentText(e2.getMessage());
							b = false;
							error.show();
						}
						if (all.getChildren().size() == 3)
							all.getChildren().remove(value);

						if (b) {
							success.setContentText("updated!");
							success.setHeaderText("Success!");
							success.show();
						}
					} else {
						success.setContentText("updated!");
						success.setHeaderText("Success!");
						success.show();
					}

					print();
				});
			} else {
				error.setContentText("This ID is NOT exist in the system!");
				error.show();
				IDText.setText("");
				nameText.setText("");
				addressText.setText("");
				mobileText.setText("");
				limited.setSelected(false);
				unlimited.setSelected(false);
			}

		});

		all.setBackground(new Background(new BackgroundImage(new Image("https://wallpaperaccess.com/full/2591633.jpg"),
				null, null, null, null)));

		scene.setRoot(all);
	}

	public void search(Scene scene) {
		GridPane searchPane = new GridPane();
		searchPane.setAlignment(Pos.CENTER);
		searchPane.setHgap(50);
		searchPane.setVgap(20);

		Label IDLable = new Label("Customer ID:");
		IDLable.setTextFill(Color.WHITE);
		IDLable.setTextFill(Color.WHITE);
		TextField IDText = new TextField();
		searchPane.add(IDLable, 0, 0);
		searchPane.add(IDText, 1, 0);

		Label nameLable = new Label("Customer Name:");
		nameLable.setTextFill(Color.WHITE);
		TextField nameText = new TextField();
		searchPane.add(nameLable, 0, 1);
		searchPane.add(nameText, 1, 1);
		nameText.setEditable(false);

		Label addressLable = new Label("Customer Address:");
		addressLable.setTextFill(Color.WHITE);
		TextField addressText = new TextField();
		searchPane.add(addressLable, 0, 2);
		searchPane.add(addressText, 1, 2);
		addressText.setEditable(false);

		Label mobileLable = new Label("Customer Mobile:");
		mobileLable.setTextFill(Color.WHITE);
		TextField mobileText = new TextField();
		searchPane.add(mobileLable, 0, 3);
		searchPane.add(mobileText, 1, 3);
		mobileText.setEditable(false);

		Label planLabel = new Label("Plan:");
		planLabel.setTextFill(Color.WHITE);
		TextField planText = new TextField();
		searchPane.add(planLabel, 0, 4);
		searchPane.add(planText, 1, 4);
		planText.setEditable(false);

		ImageView searchIcone = new ImageView("https://img.icons8.com/fluency/344/search.png");
		searchIcone.setFitWidth(35);
		searchIcone.setFitHeight(35);
		Button search = new Button("Find", searchIcone);
		search.setStyle(
				"-fx-color:lightblue;-fx-background-radius: 50; -fx-font-family: 'Comic Sans MS';-fx-font-size: 20px;");

		ImageView backIcone = new ImageView("https://img.icons8.com/color/344/return.png");
		backIcone.setFitWidth(35);
		backIcone.setFitHeight(35);
		Button back = new Button("Back", backIcone);
		back.setStyle(
				"-fx-color:lightblue;-fx-background-radius: 50; -fx-font-family: 'Comic Sans MS';-fx-font-size: 20px;");

		HBox control = new HBox(20, search, back);
		control.setAlignment(Pos.CENTER);

		VBox all = new VBox(30, searchPane, control);
		all.setAlignment(Pos.CENTER);

		IDText.setOnAction(e -> {
			int c = binaryCustomerSearch(IDText.getText());
			if (c != -1) {
				nameText.setText(system.getCustomers().get(c).getName());
				addressText.setText(system.getCustomers().get(c).getAddress());
				mobileText.setText(system.getCustomers().get(c).getMobileNum());
				planText.setText(system.getCustomers().get(c).getPlan());
				if (system.getCustomers().get(c).getPlan().equalsIgnoreCase("limited")) {
					Label valueLabel = new Label("Value:");
					valueLabel.setTextFill(Color.WHITE);
					TextField valueTextField = new TextField(system.getCustomers().get(c).getValue() + "");
					searchPane.add(valueLabel, 0, 5);
					searchPane.add(valueTextField, 1, 5);
				} else if (searchPane.getChildren().size() > 10)
					searchPane.getChildren().remove(10, 12);
			} else {
				error.setContentText("This ID is NOT in the system!");
				error.show();
				IDText.setText("");
				nameText.setText("");
				addressText.setText("");
				mobileText.setText("");
				planText.setText("");
			}

		});

		search.setOnAction(e -> {
			int c = binaryCustomerSearch(IDText.getText());
			if (c != -1) {
				nameText.setText(system.getCustomers().get(c).getName());
				addressText.setText(system.getCustomers().get(c).getAddress());
				mobileText.setText(system.getCustomers().get(c).getMobileNum());
				planText.setText(system.getCustomers().get(c).getPlan());
				if (system.getCustomers().get(c).getPlan().equalsIgnoreCase("limited")) {
					Label valueLabel = new Label("Value:");
					valueLabel.setTextFill(Color.WHITE);
					TextField valueTextField = new TextField(system.getCustomers().get(c).getValue() + "");
					searchPane.add(valueLabel, 0, 5);
					searchPane.add(valueTextField, 1, 5);
				} else if (searchPane.getChildren().size() > 10)
					searchPane.getChildren().remove(10, 12);
			} else {
				error.setContentText("This ID is NOT in the system!");
				error.show();
				IDText.setText("");
				nameText.setText("");
				addressText.setText("");
				mobileText.setText("");
				planText.setText("");
			}

		});
		back.setOnAction(e -> {
			customerInterface(scene);

		});

		all.setBackground(new Background(new BackgroundImage(new Image("https://wallpaperaccess.com/full/2591633.jpg"),
				null, null, null, null)));
		scene.setRoot(all);
	}

	public void mediaInterface(Scene scene) {
		ImageView addIcone = new ImageView("https://img.icons8.com/color/344/add--v1.png");
		addIcone.setFitWidth(35);
		addIcone.setFitHeight(35);
		Button add = new Button("Add new media", addIcone);
		add.setMinWidth(350);
		add.setMinHeight(30);
		add.setStyle(
				"-fx-color:LIGHTSLATEGRAY;-fx-background-radius: 50; -fx-font-family: 'Comic Sans MS';-fx-font-size: 20px;");

		ImageView deleteIcone = new ImageView("https://img.icons8.com/plasticine/344/filled-trash.png");
		deleteIcone.setFitWidth(35);
		deleteIcone.setFitHeight(35);
		Button remove = new Button("Delete media", deleteIcone);
		remove.setMinWidth(350);
		remove.setMinHeight(30);
		remove.setStyle(
				"-fx-color:LIGHTSLATEGRAY;-fx-background-radius: 50; -fx-font-family: 'Comic Sans MS';-fx-font-size: 20px;");

		ImageView updateIcone = new ImageView("https://img.icons8.com/flat-round/344/loop.png");
		updateIcone.setFitWidth(35);
		updateIcone.setFitHeight(35);
		Button update = new Button("Update media information ", updateIcone);
		update.setMinWidth(350);
		update.setMinHeight(30);
		update.setStyle(
				"-fx-color:LIGHTSLATEGRAY;-fx-background-radius: 50; -fx-font-family: 'Comic Sans MS';-fx-font-size: 20px;");

		ImageView searchIcone = new ImageView("https://img.icons8.com/fluency/344/search.png");
		searchIcone.setFitWidth(35);
		searchIcone.setFitHeight(35);
		Button search = new Button("Search about media by code", searchIcone);
		search.setMinWidth(350);
		search.setMinHeight(30);
		search.setStyle(
				"-fx-color:LIGHTSLATEGRAY;-fx-background-radius: 50; -fx-font-family: 'Comic Sans MS';-fx-font-size: 20px;");

		ImageView printIcone = new ImageView("https://img.icons8.com/color/344/show-property.png");
		printIcone.setFitWidth(35);
		printIcone.setFitHeight(35);
		Button print = new Button("Print All Media information", printIcone);
		print.setMinWidth(350);
		print.setMinHeight(30);
		print.setStyle(
				"-fx-color:LIGHTSLATEGRAY;-fx-background-radius: 50; -fx-font-family: 'Comic Sans MS';-fx-font-size: 20px;");

		ImageView returnIcone = new ImageView("https://img.icons8.com/color/344/return.png");
		returnIcone.setFitWidth(35);
		returnIcone.setFitHeight(35);
		Button returnButton = new Button("Return to main page", returnIcone);
		returnButton.setMinWidth(350);
		returnButton.setMinHeight(30);
		returnButton.setStyle(
				"-fx-color:LIGHTSLATEGRAY;-fx-background-radius: 50; -fx-font-family: 'Comic Sans MS';-fx-font-size: 20px;");

		GridPane mediaPane = new GridPane();
		mediaPane.setAlignment(Pos.CENTER);
		mediaPane.addColumn(0, add, remove, update, search, print, returnButton);
		mediaPane.setHgap(30);
		mediaPane.setAlignment(Pos.CENTER);
		mediaPane.setVgap(30);

		add.setOnAction(e -> {
			addMedia(scene);
		});

		remove.setOnAction(e -> {
			removeMedia(scene);
		});

		update.setOnAction(e -> {
			updateMediaInfo(scene);
		});

		search.setOnAction(e -> {
			searchMedia(scene);
		});

		print.setOnAction(e -> {
			prinInfo(scene);
		});

		returnButton.setOnAction(e -> {
			mainInterface(scene);
		});
		mediaPane.setBackground(new Background(new BackgroundImage(
				new Image("https://wallpaperaccess.com/full/2591633.jpg"), null, null, null, null)));

		scene.setRoot(mediaPane);
	}

	public void addMedia(Scene scene) {
		GridPane addPane = new GridPane();
		addPane.setAlignment(Pos.CENTER);
		addPane.setHgap(50);
		addPane.setVgap(20);

		Label codeLable = new Label("Media Code:");
		codeLable.setTextFill(Color.WHITE);
		TextField codeText = new TextField();
		addPane.add(codeLable, 0, 0);
		addPane.add(codeText, 1, 0);

		Label titleLable = new Label("Media Title:");
		titleLable.setTextFill(Color.WHITE);
		TextField titleText = new TextField();
		addPane.add(titleLable, 0, 1);
		addPane.add(titleText, 1, 1);
		titleText.setDisable(true);

		codeText.setOnKeyTyped(e -> {
			titleText.setDisable(false);
		});

		Label numberLable = new Label("number of copies:");
		numberLable.setTextFill(Color.WHITE);
		TextField numberText = new TextField();
		addPane.add(numberLable, 0, 2);
		addPane.add(numberText, 1, 2);
		numberText.setDisable(true);

		titleText.setOnKeyTyped(e -> {
			numberText.setDisable(false);
		});

		ComboBox<String> kind = new ComboBox<>();
		kind.getItems().addAll("Album", "Movie", "Game");
		kind.setDisable(true);

		numberText.setOnKeyTyped(e -> {
			kind.setDisable(false);
		});

		ImageView addIcone = new ImageView("https://img.icons8.com/color/344/add--v1.png");
		addIcone.setFitWidth(35);
		addIcone.setFitHeight(35);
		Button add = new Button("add", addIcone);
		add.setDisable(true);
		add.setStyle(
				"-fx-color:lightblue;-fx-background-radius: 50; -fx-font-family: 'Comic Sans MS';-fx-font-size: 20px;");

		ImageView backIcone = new ImageView("https://img.icons8.com/color/344/return.png");
		backIcone.setFitWidth(35);
		backIcone.setFitHeight(35);
		Button back = new Button("Back", backIcone);
		HBox control = new HBox(20, add, back);
		control.setAlignment(Pos.CENTER);
		back.setStyle(
				"-fx-color:lightblue;-fx-background-radius: 50; -fx-font-family: 'Comic Sans MS';-fx-font-size: 20px;");

		VBox all = new VBox(30, addPane, kind, control);
		all.setAlignment(Pos.CENTER);
		back.setOnAction(e -> {
			mediaInterface(scene);

		});

		kind.setOnAction(e -> {
			s = kind.getValue();
			add.setDisable(true);
			if (s.equals("Album")) {
				if (addPane.getChildren().size() > 6)
					addPane.getChildren().remove(6, addPane.getChildren().size());
				// addPane.getChildren().remove(3, all.getChildren().size());
				Label artistLable = new Label("Artist name:");
				artistLable.setTextFill(Color.WHITE);
				TextField artistText = new TextField();
				addPane.add(artistLable, 0, 3);
				addPane.add(artistText, 1, 3);

				Label songsLable = new Label("Songs names:");
				songsLable.setTextFill(Color.WHITE);
				TextField songsText = new TextField();
				addPane.add(songsLable, 0, 4);
				addPane.add(songsText, 1, 4);
				songsText.setDisable(true);

				artistText.setOnKeyTyped(e1 -> {
					songsText.setDisable(false);
				});

				songsText.setOnKeyTyped(e1 -> {
					add.setDisable(false);
				});

				add.setOnAction(e1 -> {
					try {
						system.addAlbum(codeText.getText(), titleText.getText(), Integer.parseInt(numberText.getText()),
								artistText.getText(), songsText.getText());
						success.setContentText("Added!");
						success.setHeaderText("Success");
						success.show();
						print();
					} catch (NumberFormatException ex) {
						error.setContentText("You should enter number in number of copies field !!");
						error.show();
					}
				});

			}

			else if (s.equals("Movie")) {
				if (addPane.getChildren().size() > 6)
					addPane.getChildren().remove(6, addPane.getChildren().size());
				Label ratingLable = new Label("Rating of movie:");
				ratingLable.setTextFill(Color.WHITE);
				RadioButton ac = new RadioButton("AC");
				ac.setTextFill(Color.WHITE);
				RadioButton hr = new RadioButton("HR");
				hr.setTextFill(Color.WHITE);
				RadioButton dr = new RadioButton("DR");
				ToggleGroup ratingGroup = new ToggleGroup();
				dr.setTextFill(Color.WHITE);
				ac.setToggleGroup(ratingGroup);
				hr.setToggleGroup(ratingGroup);
				dr.setToggleGroup(ratingGroup);

				dr.setOnAction(e1 -> {
					s = "DR";
					add.setDisable(false);
				});

				hr.setOnAction(e1 -> {
					s = "HR";
					add.setDisable(false);
				});

				ac.setOnAction(e1 -> {
					s = "AC";
					add.setDisable(false);
				});

				HBox choice = new HBox(5, ac, hr, dr);
				choice.setAlignment(Pos.CENTER);
				addPane.add(ratingLable, 0, 3);
				addPane.add(choice, 1, 3);

				add.setOnAction(e1 -> {
					try {
						system.addMovie(codeText.getText(), titleText.getText(), Integer.parseInt(numberText.getText()),
								s);
						success.setContentText("Added!");
						success.setHeaderText("Success");
						success.show();
						print();
					} catch (NumberFormatException ex) {
						error.setContentText("You should enter number in number of copies field !!");
						error.show();
					}
				});

			}

			else {
				if (addPane.getChildren().size() > 6)
					addPane.getChildren().remove(6, addPane.getChildren().size());
				Label weightLable = new Label("Weight of movie:");
				weightLable.setTextFill(Color.WHITE);
				TextField weightText = new TextField();
				addPane.add(weightLable, 0, 3);
				addPane.add(weightText, 1, 3);

				weightText.setOnKeyTyped(e1 -> {
					add.setDisable(false);
				});

				add.setOnAction(e1 -> {
					try {
						system.addGame(codeText.getText(), titleText.getText(), Integer.parseInt(numberText.getText()),
								Double.parseDouble(weightText.getText()));
						success.setContentText("Added!");
						success.setHeaderText("Success");
						success.show();
						print();
					} catch (NumberFormatException ex) {
						error.setContentText("You should enter number in number of copies field !!");
						error.show();
					}
				});

			}
		});

		all.setBackground(new Background(new BackgroundImage(new Image("https://wallpaperaccess.com/full/2591633.jpg"),
				null, null, null, null)));

		scene.setRoot(all);
	}

	public void removeMedia(Scene scene) {
		GridPane removePane = new GridPane();
		removePane.setAlignment(Pos.CENTER);
		removePane.setHgap(50);
		removePane.setVgap(20);

		Label codeLable = new Label("Media Code:");
		codeLable.setTextFill(Color.WHITE);
		TextField codeText = new TextField();
		removePane.add(codeLable, 0, 0);
		removePane.add(codeText, 1, 0);

		Label titleLable = new Label("Media Title:");
		titleLable.setTextFill(Color.WHITE);
		TextField titleText = new TextField();
		removePane.add(titleLable, 0, 1);
		removePane.add(titleText, 1, 1);
		titleText.setEditable(false);

		Label numberLable = new Label("number of copies:");
		numberLable.setTextFill(Color.WHITE);
		TextField numberText = new TextField();
		removePane.add(numberLable, 0, 2);
		removePane.add(numberText, 1, 2);
		numberText.setEditable(false);

		ImageView searchIcone = new ImageView("https://img.icons8.com/fluency/344/search.png");
		searchIcone.setFitWidth(35);
		searchIcone.setFitHeight(35);
		Button search = new Button("Find", searchIcone);
		search.setStyle(
				"-fx-color:lightblue;-fx-background-radius: 50; -fx-font-family: 'Comic Sans MS';-fx-font-size: 20px;");

		ImageView deleteIcone = new ImageView("https://img.icons8.com/plasticine/344/filled-trash.png");
		deleteIcone.setFitWidth(35);
		deleteIcone.setFitHeight(35);
		Button delete = new Button("remove", deleteIcone);
		delete.setDisable(true);
		delete.setStyle(
				"-fx-color:lightblue;-fx-background-radius: 50; -fx-font-family: 'Comic Sans MS';-fx-font-size: 20px;");

		ImageView backIcone = new ImageView("https://img.icons8.com/color/344/return.png");
		backIcone.setFitWidth(35);
		backIcone.setFitHeight(35);
		Button back = new Button("Back", backIcone);
		back.setStyle(
				"-fx-color:lightblue;-fx-background-radius: 50; -fx-font-family: 'Comic Sans MS';-fx-font-size: 20px;");

		HBox control = new HBox(20, search, delete, back);
		control.setAlignment(Pos.CENTER);

		VBox all = new VBox(30, removePane, control);
		all.setAlignment(Pos.CENTER);
		back.setOnAction(e -> {
			mediaInterface(scene);

		});

		search.setOnAction(e -> {
			int m = binaryMediaSearch(codeText.getText());
			if (m != -1) {
				delete.setDisable(false);
				if (system.getMedia().get(m) instanceof Album) {
					if (removePane.getChildren().size() > 6)
						removePane.getChildren().remove(6, removePane.getChildren().size());

					Label kindLabel = new Label("Kind:");
					kindLabel.setTextFill(Color.WHITE);
					TextField kindText = new TextField("Album");
					removePane.add(kindLabel, 0, 3);
					removePane.add(kindText, 1, 3);
					kindText.setEditable(false);

					Label artistLable = new Label("Artist name:");
					artistLable.setTextFill(Color.WHITE);
					TextField artistText = new TextField();
					removePane.add(artistLable, 0, 4);
					removePane.add(artistText, 1, 4);
					artistText.setEditable(false);

					Label songsLable = new Label("Songs names:");
					songsLable.setTextFill(Color.WHITE);
					TextField songsText = new TextField();
					removePane.add(songsLable, 0, 5);
					removePane.add(songsText, 1, 5);
					songsText.setEditable(false);

					titleText.setText(system.getMedia().get(m).getTitle());
					numberText.setText(system.getMedia().get(m).getNumOfCopies() + "");
					artistText.setText(((Album) system.getMedia().get(m)).getArtist());
					songsText.setText(((Album) system.getMedia().get(m)).getSongs());

					delete.setOnAction(e1 -> {
						system.getMedia().remove(m);
						codeText.setText("");
						titleText.setText("");
						numberText.setText("");
						removePane.getChildren().remove(6, removePane.getChildren().size());
						success.setContentText("Deleted!");
						success.setHeaderText("Success");
						success.show();
						print();
					});

				} else if (system.getMedia().get(m) instanceof Movie) {
					if (removePane.getChildren().size() > 6)
						removePane.getChildren().remove(6, removePane.getChildren().size());

					Label kindLabel = new Label("Kind:");
					kindLabel.setTextFill(Color.WHITE);
					TextField kindText = new TextField("Movie");
					removePane.add(kindLabel, 0, 3);
					removePane.add(kindText, 1, 3);
					kindText.setEditable(false);

					Label ratingLable = new Label("Rating of movie:");
					ratingLable.setTextFill(Color.WHITE);
					TextField ratingText = new TextField();
					removePane.add(ratingLable, 0, 4);
					removePane.add(ratingText, 1, 4);
					ratingText.setEditable(false);

					titleText.setText(system.getMedia().get(m).getTitle());
					numberText.setText(system.getMedia().get(m).getNumOfCopies() + "");
					ratingText.setText(((Movie) system.getMedia().get(m)).getRating());

					delete.setOnAction(e1 -> {
						system.getMedia().remove(m);
						codeText.setText("");
						titleText.setText("");
						numberText.setText("");
						removePane.getChildren().remove(6, removePane.getChildren().size());
						success.setContentText("Deleted!");
						success.setHeaderText("Success");
						success.show();
					});

				} else {
					if (removePane.getChildren().size() > 6)
						removePane.getChildren().remove(6, removePane.getChildren().size());

					Label kindLabel = new Label("Kind:");
					kindLabel.setTextFill(Color.WHITE);
					TextField kindText = new TextField("Game");
					removePane.add(kindLabel, 0, 3);
					removePane.add(kindText, 1, 3);
					kindText.setEditable(false);

					Label weightLable = new Label("Weight of movie:");
					weightLable.setTextFill(Color.WHITE);
					TextField weightText = new TextField();
					removePane.add(weightLable, 0, 4);
					removePane.add(weightText, 1, 4);
					weightText.setEditable(false);

					titleText.setText(system.getMedia().get(m).getTitle());
					numberText.setText(system.getMedia().get(m).getNumOfCopies() + "");
					weightText.setText(((Game) system.getMedia().get(m)).getWeight() + "");

					delete.setOnAction(e1 -> {
						system.getMedia().remove(m);
						codeText.setText("");
						titleText.setText("");
						numberText.setText("");
						removePane.getChildren().remove(6, removePane.getChildren().size());
						success.setContentText("Deleted!");
						success.setHeaderText("Success");
						success.show();
						print();
					});

				}
			} else {
				delete.setDisable(true);
				error.setContentText("There are NOT media with this code!");
				error.show();
				delete.setOnAction(e1 -> {
					error.setContentText("You can't Delete because this code is not exist in system!!");
					error.show();
				});
			}

		});

		all.setBackground(new Background(new BackgroundImage(new Image("https://wallpaperaccess.com/full/2591633.jpg"),
				null, null, null, null)));

		scene.setRoot(all);
	}

	private int binaryMediaSearch(String key) {
		ArrayList<Media> media = system.getMedia();
		int l = 0, h = media.size() - 1;
		while (l <= h) {
			int mid = (l + h) / 2;
			if (media.get(mid).getCode().compareToIgnoreCase(key) > 0)
				h = mid - 1;
			else if (media.get(mid).getCode().compareToIgnoreCase(key) < 0)
				l = mid + 1;
			else
				return mid;
		}

		return -1;
	}

	public void updateMediaInfo(Scene scene) {
		GridPane updatePane = new GridPane();
		updatePane.setAlignment(Pos.CENTER);
		updatePane.setHgap(50);
		updatePane.setVgap(20);

		Label codeLable = new Label("Media Code:");
		codeLable.setTextFill(Color.WHITE);
		TextField codeText = new TextField();
		updatePane.add(codeLable, 0, 0);
		updatePane.add(codeText, 1, 0);

		Label titleLable = new Label("Media Title:");
		titleLable.setTextFill(Color.WHITE);
		TextField titleText = new TextField();
		updatePane.add(titleLable, 0, 1);
		updatePane.add(titleText, 1, 1);
		titleText.setEditable(false);

		Label numberLable = new Label("number of copies:");
		numberLable.setTextFill(Color.WHITE);
		TextField numberText = new TextField();
		updatePane.add(numberLable, 0, 2);
		updatePane.add(numberText, 1, 2);
		numberText.setEditable(false);

		ImageView searchIcone = new ImageView("https://img.icons8.com/fluency/344/search.png");
		searchIcone.setFitWidth(35);
		searchIcone.setFitHeight(35);
		Button search = new Button("Find", searchIcone);
		search.setStyle(
				"-fx-color:lightblue;-fx-background-radius: 50; -fx-font-family: 'Comic Sans MS';-fx-font-size: 20px;");

		ImageView updateIcone = new ImageView("https://img.icons8.com/flat-round/344/loop.png");
		updateIcone.setFitWidth(35);
		updateIcone.setFitHeight(35);
		Button update = new Button("update", updateIcone);
		update.setDisable(true);
		update.setStyle(
				"-fx-color:lightblue;-fx-background-radius: 50; -fx-font-family: 'Comic Sans MS';-fx-font-size: 20px;");

		ImageView backIcone = new ImageView("https://img.icons8.com/color/344/return.png");
		backIcone.setFitWidth(35);
		backIcone.setFitHeight(35);
		Button back = new Button("Back", backIcone);
		back.setStyle(
				"-fx-color:lightblue;-fx-background-radius: 50; -fx-font-family: 'Comic Sans MS';-fx-font-size: 20px;");

		HBox control = new HBox(20, search, update, back);
		control.setAlignment(Pos.CENTER);

		VBox all = new VBox(30, updatePane, control);
		all.setAlignment(Pos.CENTER);
		back.setOnAction(e -> {
			mediaInterface(scene);

		});
		search.setOnAction(e -> {
			int m = binaryMediaSearch(codeText.getText());
			if (m != -1) {
				update.setDisable(false);
				titleText.setEditable(true);
				numberText.setEditable(true);
				if (system.getMedia().get(m) instanceof Album) {
					if (updatePane.getChildren().size() > 6)
						updatePane.getChildren().remove(6, updatePane.getChildren().size());

					Label kindLabel = new Label("Kind:");
					kindLabel.setTextFill(Color.WHITE);
					TextField kindText = new TextField("Album");
					updatePane.add(kindLabel, 0, 3);
					updatePane.add(kindText, 1, 3);
					kindText.setEditable(false);

					Label artistLable = new Label("Artist name:");
					artistLable.setTextFill(Color.WHITE);
					TextField artistText = new TextField();
					updatePane.add(artistLable, 0, 4);
					updatePane.add(artistText, 1, 4);

					Label songsLable = new Label("Songs names:");
					songsLable.setTextFill(Color.WHITE);
					TextField songsText = new TextField();
					updatePane.add(songsLable, 0, 5);
					updatePane.add(songsText, 1, 5);

					titleText.setText(system.getMedia().get(m).getTitle());
					numberText.setText(system.getMedia().get(m).getNumOfCopies() + "");
					artistText.setText(((Album) system.getMedia().get(m)).getArtist());
					songsText.setText(((Album) system.getMedia().get(m)).getSongs());
					titleText.setEditable(true);
					numberText.setEditable(true);

					update.setOnAction(e1 -> {
						try {
							system.getMedia().get(m).setNumOfCopies(Integer.parseInt(numberText.getText()));
							success.setContentText("updated!");
							success.setHeaderText("Success");
							success.show();
						} catch (NumberFormatException ex) {
							error.setContentText("You should enter number in number of copies field !!");
							error.show();
						}
						system.getMedia().get(m).setTitle(titleText.getText());
						((Album) system.getMedia().get(m)).setArtist(artistText.getText());
						((Album) system.getMedia().get(m)).setSongs(songsText.getText());
						print();
					});

				} else if (system.getMedia().get(m) instanceof Movie) {
					if (updatePane.getChildren().size() > 6)
						updatePane.getChildren().remove(6, updatePane.getChildren().size());

					Label kindLabel = new Label("Kind:");
					kindLabel.setTextFill(Color.WHITE);
					TextField kindText = new TextField("Movie");
					updatePane.add(kindLabel, 0, 3);
					updatePane.add(kindText, 1, 3);
					kindText.setEditable(false);

					Label ratingLable = new Label("Rating of movie:");
					ratingLable.setTextFill(Color.WHITE);
					RadioButton ac = new RadioButton("AC");
					ac.setTextFill(Color.WHITE);
					RadioButton hr = new RadioButton("HR");
					hr.setTextFill(Color.WHITE);
					RadioButton dr = new RadioButton("DR");
					dr.setTextFill(Color.WHITE);
					ToggleGroup ratingGroup = new ToggleGroup();
					ac.setToggleGroup(ratingGroup);
					hr.setToggleGroup(ratingGroup);
					dr.setToggleGroup(ratingGroup);
					HBox choice = new HBox(5, ac, hr, dr);
					choice.setAlignment(Pos.CENTER);

					updatePane.add(ratingLable, 0, 4);
					updatePane.add(choice, 1, 4);

					titleText.setText(system.getMedia().get(m).getTitle());
					numberText.setText(system.getMedia().get(m).getNumOfCopies() + "");

					s = ((Movie) system.getMedia().get(m)).getRating();
					if (((Movie) system.getMedia().get(m)).getRating().equals("AC"))
						ac.setSelected(true);
					else if (((Movie) system.getMedia().get(m)).getRating().equals("HR"))
						hr.setSelected(true);
					else
						dr.setSelected(true);

					titleText.setEditable(true);
					numberText.setEditable(true);

					dr.setOnAction(e2 -> {
						s = "DR";
					});

					hr.setOnAction(e2 -> {
						s = "HR";
					});

					ac.setOnAction(e2 -> {
						s = "AC";
					});

					update.setOnAction(e1 -> {
						try {
							system.getMedia().get(m).setNumOfCopies(Integer.parseInt(numberText.getText()));
							success.setContentText("updated!");
							success.setHeaderText("Success");
							success.show();
						} catch (NumberFormatException ex) {
							error.setContentText("You should enter number in number of copies field !!");
							error.show();
						}
						system.getMedia().get(m).setTitle(titleText.getText());
						((Movie) (system.getMedia().get(m))).setRating(s);
						print();
					});

				} else {
					if (updatePane.getChildren().size() > 6)
						updatePane.getChildren().remove(6, updatePane.getChildren().size());

					Label kindLabel = new Label("Kind:");
					kindLabel.setTextFill(Color.WHITE);
					TextField kindText = new TextField("Game");
					updatePane.add(kindLabel, 0, 3);
					updatePane.add(kindText, 1, 3);
					kindText.setEditable(false);

					Label weightLable = new Label("Weight of movie:");
					weightLable.setTextFill(Color.WHITE);
					TextField weightText = new TextField();
					updatePane.add(weightLable, 0, 4);
					updatePane.add(weightText, 1, 4);

					titleText.setText(system.getMedia().get(m).getTitle());
					numberText.setText(system.getMedia().get(m).getNumOfCopies() + "");
					weightText.setText(((Game) system.getMedia().get(m)).getWeight() + "");

					titleText.setEditable(true);
					numberText.setEditable(true);

					update.setOnAction(e1 -> {
						try {
							system.getMedia().get(m).setNumOfCopies(Integer.parseInt(numberText.getText()));
						} catch (NumberFormatException ex) {
							error.setContentText("You should enter number in number of copies field !!");
							error.show();
						}
						system.getMedia().get(m).setTitle(titleText.getText());
						try {
							((Game) (system.getMedia().get(m))).setWeight(Double.parseDouble(numberText.getText()));
							success.setContentText("updated!");
							success.setHeaderText("Success");
							success.show();
						} catch (NumberFormatException ex) {
							error.setContentText("You should enter number in weight field !!");
							error.show();
						}
						print();
					});

				}
			} else {
				update.setDisable(true);
				error.setContentText("This code is NOT exist in the system!");
				error.show();
				update.setOnAction(e1 -> {
					error.setContentText("You can't Delete because this code is not exist in system!!");
				});
			}

		});

		all.setBackground(new Background(new BackgroundImage(new Image("https://wallpaperaccess.com/full/2591633.jpg"),
				null, null, null, null)));
		scene.setRoot(all);
	}

	public void searchMedia(Scene scene) {
		GridPane searchPane = new GridPane();
		searchPane.setAlignment(Pos.CENTER);
		searchPane.setHgap(50);
		searchPane.setVgap(20);

		Label codeLable = new Label("Media Code:");
		codeLable.setTextFill(Color.WHITE);
		TextField codeText = new TextField();
		searchPane.add(codeLable, 0, 0);
		searchPane.add(codeText, 1, 0);

		Label titleLable = new Label("Media Title:");
		titleLable.setTextFill(Color.WHITE);
		TextField titleText = new TextField();
		searchPane.add(titleLable, 0, 1);
		searchPane.add(titleText, 1, 1);
		titleText.setEditable(false);

		Label numberLable = new Label("number of copies:");
		numberLable.setTextFill(Color.WHITE);
		TextField numberText = new TextField();
		searchPane.add(numberLable, 0, 2);
		searchPane.add(numberText, 1, 2);
		numberText.setEditable(false);

		ImageView searchIcone = new ImageView("https://img.icons8.com/fluency/344/search.png");
		searchIcone.setFitWidth(35);
		searchIcone.setFitHeight(35);
		Button search = new Button("Find", searchIcone);
		search.setStyle(
				"-fx-color:lightblue;-fx-background-radius: 50; -fx-font-family: 'Comic Sans MS';-fx-font-size: 20px;");

		ImageView backIcone = new ImageView("https://img.icons8.com/color/344/return.png");
		backIcone.setFitWidth(35);
		backIcone.setFitHeight(35);
		Button back = new Button("Back", backIcone);
		back.setStyle(
				"-fx-color:lightblue;-fx-background-radius: 50; -fx-font-family: 'Comic Sans MS';-fx-font-size: 20px;");

		HBox control = new HBox(20, search, back);
		control.setAlignment(Pos.CENTER);

		VBox all = new VBox(30, searchPane, control);
		all.setAlignment(Pos.CENTER);
		back.setOnAction(e -> {
			mediaInterface(scene);

		});

		search.setOnAction(e -> {
			int m = binaryMediaSearch(codeText.getText());
			if (m != -1) {
				if (system.getMedia().get(m) instanceof Album) {
					if (searchPane.getChildren().size() > 6)
						searchPane.getChildren().remove(6, searchPane.getChildren().size());

					Label kindLabel = new Label("Kind:");
					kindLabel.setTextFill(Color.WHITE);
					TextField kindText = new TextField("Album");
					searchPane.add(kindLabel, 0, 3);
					searchPane.add(kindText, 1, 3);
					kindText.setEditable(false);

					Label artistLable = new Label("Artist name:");
					artistLable.setTextFill(Color.WHITE);
					TextField artistText = new TextField();
					searchPane.add(artistLable, 0, 4);
					searchPane.add(artistText, 1, 4);
					artistText.setEditable(false);

					Label songsLable = new Label("Songs names:");
					songsLable.setTextFill(Color.WHITE);
					TextField songsText = new TextField();
					searchPane.add(songsLable, 0, 5);
					searchPane.add(songsText, 1, 5);
					songsText.setEditable(false);

					titleText.setText(system.getMedia().get(m).getTitle());
					numberText.setText(system.getMedia().get(m).getNumOfCopies() + "");
					artistText.setText(((Album) system.getMedia().get(m)).getArtist());
					songsText.setText(((Album) system.getMedia().get(m)).getSongs());

				} else if (system.getMedia().get(m) instanceof Movie) {
					if (searchPane.getChildren().size() > 6)
						searchPane.getChildren().remove(6, searchPane.getChildren().size());

					Label kindLabel = new Label("Kind:");
					kindLabel.setTextFill(Color.WHITE);
					TextField kindText = new TextField("Movie");
					searchPane.add(kindLabel, 0, 3);
					searchPane.add(kindText, 1, 3);
					kindText.setEditable(false);

					Label ratingLable = new Label("Rating of movie:");
					ratingLable.setTextFill(Color.WHITE);
					TextField ratingText = new TextField();
					searchPane.add(ratingLable, 0, 4);
					searchPane.add(ratingText, 1, 4);
					ratingText.setEditable(false);

					titleText.setText(system.getMedia().get(m).getTitle());
					numberText.setText(system.getMedia().get(m).getNumOfCopies() + "");
					ratingText.setText(((Movie) system.getMedia().get(m)).getRating());

				} else {
					if (searchPane.getChildren().size() > 6)
						searchPane.getChildren().remove(6, searchPane.getChildren().size());

					Label kindLabel = new Label("Kind:");
					kindLabel.setTextFill(Color.WHITE);
					TextField kindText = new TextField("Game");
					searchPane.add(kindLabel, 0, 3);
					searchPane.add(kindText, 1, 3);
					kindText.setEditable(false);

					Label weightLable = new Label("Weight of movie:");
					weightLable.setTextFill(Color.WHITE);
					TextField weightText = new TextField();
					searchPane.add(weightLable, 0, 4);
					searchPane.add(weightText, 1, 4);
					weightText.setEditable(false);

					titleText.setText(system.getMedia().get(m).getTitle());
					numberText.setText(system.getMedia().get(m).getNumOfCopies() + "");
					weightText.setText(((Game) system.getMedia().get(m)).getWeight() + "");

				}
			} else {
				error.setContentText("This code is NOT exist in the system!");
				error.show();
			}

		});

		all.setBackground(new Background(new BackgroundImage(new Image("https://wallpaperaccess.com/full/2591633.jpg"),
				null, null, null, null)));

		scene.setRoot(all);
	}

	public void prinInfo(Scene scene) {
		TextArea media = new TextArea(system.getAllMediaInfo());
		if (system.getMedia().size() < 1)
			media.setText("There are NO media in your system.");
		media.setMaxWidth(600);
		media.setMinWidth(600);
		ImageView backIcone = new ImageView("https://img.icons8.com/color/344/return.png");
		backIcone.setFitWidth(35);
		backIcone.setFitHeight(35);
		Button back = new Button("Back", backIcone);
		back.setStyle(
				"-fx-color:lightblue;-fx-background-radius: 50; -fx-font-family: 'Comic Sans MS';-fx-font-size: 20px;");

		VBox all = new VBox(20, media, back);
		all.setAlignment(Pos.CENTER);
		back.setOnAction(e -> {
			mediaInterface(scene);

		});
		all.setBackground(new Background(new BackgroundImage(new Image("https://wallpaperaccess.com/full/2591633.jpg"),
				null, null, null, null)));
		scene.setRoot(all);
	}

	public void rentInterface(Scene scene) {
		ImageView cartIcone = new ImageView(
				"https://img.icons8.com/external-icongeek26-linear-colour-icongeek26/344/external-cart-ecommerce-icongeek26-linear-colour-icongeek26.png");
		cartIcone.setFitWidth(50);
		cartIcone.setFitHeight(50);
		Button cart = new Button("Add To cart and procces", cartIcone);
		cart.setStyle(
				"-fx-color:LIGHTSLATEGRAY;-fx-background-radius: 50; -fx-font-family: 'Comic Sans MS';-fx-font-size: 20px;");
		cart.setMinSize(500, 75);

		ImageView cartPrintIcon = new ImageView("https://img.icons8.com/color/344/show-property.png");
		cartPrintIcon.setFitWidth(50);
		cartPrintIcon.setFitHeight(50);
		Button cartPrint = new Button("Print the items that in the customer cart", cartPrintIcon);
		cartPrint.setMinWidth(300);
		cartPrint.setMinHeight(75);
		cartPrint.setMinSize(500, 75);
		cartPrint.setStyle(
				"-fx-color:LIGHTSLATEGRAY;-fx-background-radius: 50; -fx-font-family: 'Comic Sans MS';-fx-font-size: 20px;");

		ImageView recicedPrintIcone = new ImageView("https://img.icons8.com/color/344/show-property.png");
		recicedPrintIcone.setFitWidth(50);
		recicedPrintIcone.setFitHeight(50);
		Button recivedPrint = new Button("Print the items that in recevid by the user", recicedPrintIcone);
		recivedPrint.setMinWidth(300);
		recivedPrint.setMinHeight(75);
		recivedPrint.setMinSize(500, 75);
		recivedPrint.setStyle(
				"-fx-color:LIGHTSLATEGRAY;-fx-background-radius: 50; -fx-font-family: 'Comic Sans MS';-fx-font-size: 20px;");

		ImageView returnIcone = new ImageView(
				"https://img.icons8.com/external-linector-lineal-color-linector/344/external-return-online-shopping-linector-lineal-color-linector.png");
		returnIcone.setFitWidth(50);
		returnIcone.setFitHeight(50);
		Button returnButton = new Button("Return a media that alrady recived by user.", returnIcone);
		returnButton.setStyle(
				"-fx-color:LIGHTSLATEGRAY;-fx-background-radius: 50; -fx-font-family: 'Comic Sans MS';-fx-font-size: 20px;");
		returnButton.setMinSize(500, 75);

		ImageView backIcone = new ImageView("https://img.icons8.com/color/344/return.png");
		backIcone.setFitWidth(50);
		backIcone.setFitHeight(50);
		Button back = new Button("Back", backIcone);
		back.setMinWidth(300);
		back.setMinHeight(75);
		back.setMinSize(500, 75);
		back.setStyle(
				"-fx-color:LIGHTSLATEGRAY;-fx-background-radius: 50; -fx-font-family: 'Comic Sans MS';-fx-font-size: 20px;");

		VBox rentBox = new VBox(30, cart, cartPrint, recivedPrint, returnButton, back);
		rentBox.setAlignment(Pos.CENTER);

		rentBox.setAlignment(Pos.CENTER);
		cart.setOnAction(e -> {
			addToCartAndProcces(scene);
		});

		cartPrint.setOnAction(e -> {
			showCart(scene);
		});
		recivedPrint.setOnAction(e -> {
			showRecived(scene);
		});
		returnButton.setOnAction(e -> {
			returnInterface(scene);
		});
		back.setOnAction(e -> {
			mainInterface(scene);

		});

		rentBox.setBackground(new Background(new BackgroundImage(
				new Image("https://wallpaperaccess.com/full/2591633.jpg"), null, null, null, null)));
		scene.setRoot(rentBox);
	}

	public void returnInterface(Scene scene) {
		GridPane returnPane = new GridPane();
		returnPane.setAlignment(Pos.CENTER);
		returnPane.setHgap(50);
		returnPane.setVgap(20);

		Label IDLable = new Label("Customer ID:");
		IDLable.setTextFill(Color.WHITE);
		TextField IDText = new TextField();
		returnPane.add(IDLable, 0, 0);
		returnPane.add(IDText, 1, 0);
		TextArea customerInfo = new TextArea();
		customerInfo.setEditable(false);
		returnPane.add(customerInfo, 1, 1);
		customerInfo.setMinHeight(50);

		HBox customarAndMedia = new HBox(30, returnPane);
		customarAndMedia.setAlignment(Pos.CENTER);

		ImageView returnIcone = new ImageView(
				"https://img.icons8.com/external-linector-lineal-color-linector/344/external-return-online-shopping-linector-lineal-color-linector.png");
		returnIcone.setFitWidth(35);
		returnIcone.setFitHeight(35);
		Button returnButton = new Button("return", returnIcone);
		returnButton.setDisable(true);
		returnButton.setStyle(
				"-fx-color:lightblue;-fx-background-radius: 50; -fx-font-family: 'Comic Sans MS';-fx-font-size: 20px;");

		Label codeLable = new Label("Media Code:");
		codeLable.setTextFill(Color.WHITE);
		TextField codeText = new TextField();
		returnPane.add(codeLable, 0, 2);
		returnPane.add(codeText, 1, 2);

		TextArea mediaInfo = new TextArea();
		mediaInfo.setMinHeight(50);
		mediaInfo.setEditable(false);
		returnPane.add(mediaInfo, 1, 3);

		IDText.setOnKeyTyped(e -> {
			i = binaryCustomerSearch(IDText.getText());
			if (i >= 0) {
				Customer m = system.getCustomers().get(i);
				customerInfo.setText("The customer have Recived list: " + m.getReceived());
			} else {
				s = "This Customer is NOT exist in system! ";
				customerInfo.setText(s);
				returnButton.setDisable(true);
			}
			if (j >= 0 && i >= 0) {
				int x = system.getCustomers().get(i).getReceived().indexOf(system.getMedia().get(j).getCode());
				if (x > -1) {
					mediaInfo.appendText("\nThe customer can return it.");
					returnButton.setDisable(false);
					returnButton.setOnAction(e1 -> {
						system.returnMedia(IDText.getText(), codeText.getText());
						print();
					});
				} else {
					returnButton.setDisable(true);
					customerInfo.setText(s + " the customer can't return it (it's NOT in his recived list).");
				}
			} else
				returnButton.setDisable(true);
		});

		codeText.setOnKeyTyped(e -> {
			j = binaryMediaSearch(codeText.getText());
			if (j >= 0) {
				mediaInfo.setText("This code is exist in the system");
			} else {
				s = "This code is NOT exist in system";
				mediaInfo.setText(s);
				returnButton.setDisable(true);
			}
			if (j >= 0 && i >= 0) {
				int x = system.getCustomers().get(i).getReceived().indexOf(system.getMedia().get(j).getCode());
				if (x > -1) {
					mediaInfo.appendText("\nThe customer can return it.");
					returnButton.setDisable(false);
					returnButton.setOnAction(e1 -> {
						system.returnMedia(IDText.getText(), codeText.getText());
						print();
					});
				} else {
					returnButton.setDisable(true);
					mediaInfo.appendText("\nThe customer can't return it (it's NOT in his recived list).");
				}
			} else
				returnButton.setDisable(true);
		});

		ImageView backIcone = new ImageView("https://img.icons8.com/color/344/return.png");
		backIcone.setFitWidth(35);
		backIcone.setFitHeight(35);
		Button back = new Button("Back", backIcone);
		back.setStyle(
				"-fx-color:lightblue;-fx-background-radius: 50; -fx-font-family: 'Comic Sans MS';-fx-font-size: 20px;");

		HBox control = new HBox(20, returnButton, back);
		control.setAlignment(Pos.CENTER);

		back.setOnAction(e -> {
			rentInterface(scene);

		});

		VBox all = new VBox(20, customarAndMedia, control);
		all.setAlignment(Pos.CENTER);

		all.setBackground(new Background(new BackgroundImage(new Image("https://wallpaperaccess.com/full/2591633.jpg"),
				null, null, null, null)));
		scene.setRoot(all);
	}

	public void showRecived(Scene scene) {
		GridPane recivedPane = new GridPane();
		recivedPane.setAlignment(Pos.CENTER);
		recivedPane.setHgap(50);
		recivedPane.setVgap(20);

		Label IDLable = new Label("Customer ID:");
		IDLable.setTextFill(Color.WHITE);
		TextField IDText = new TextField();
		recivedPane.add(IDLable, 0, 0);
		recivedPane.add(IDText, 1, 0);

		TextArea info = new TextArea();
		info.setEditable(false);
		IDText.setOnKeyTyped(e -> {
			int i = binaryCustomerSearch(IDText.getText());
			String s = new String();
			if (i >= 0) {
				if (system.getCustomers().get(i).getReceived().size() > 0) {
					Customer customer = system.getCustomers().get(i);
					s = "";
					for (int j = 0; j < customer.getReceived().size(); j++) {
						s += "Code: " + customer.getReceived().get(j) + "\n";
						s += "Title: " + getTitle(customer.getReceived().get(j)) + "\n**************************\n";
					}
				} else
					s = "The user is NOT have any media recived.";

			} else
				s = "This id is NOT exist in system.";
			info.setText(s);
		});
		Label cartLabel = new Label("The Recived:");
		cartLabel.setTextFill(Color.WHITE);

		recivedPane.add(cartLabel, 0, 2);
		recivedPane.add(info, 1, 2);

		ImageView backIcone = new ImageView("https://img.icons8.com/color/344/return.png");
		backIcone.setFitWidth(35);
		backIcone.setFitHeight(35);
		Button back = new Button("Back", backIcone);
		back.setStyle(
				"-fx-color:lightblue;-fx-background-radius: 50; -fx-font-family: 'Comic Sans MS';-fx-font-size: 20px;");

		VBox box = new VBox(20, recivedPane, back);
		box.setAlignment(Pos.CENTER);

		back.setOnAction(e -> {
			rentInterface(scene);

		});

		box.setBackground(new Background(new BackgroundImage(new Image("https://wallpaperaccess.com/full/2591633.jpg"),
				null, null, null, null)));

		scene.setRoot(box);
	}

	public void showCart(Scene scene) {
		GridPane cartPane = new GridPane();
		cartPane.setAlignment(Pos.CENTER);
		cartPane.setHgap(50);
		cartPane.setVgap(20);

		Label IDLable = new Label("Customer ID:");
		IDLable.setTextFill(Color.WHITE);
		TextField IDText = new TextField();
		cartPane.add(IDLable, 0, 0);
		cartPane.add(IDText, 1, 0);

		TextArea info = new TextArea();
		info.setEditable(false);

		IDText.setOnKeyTyped(e -> {
			int i = binaryCustomerSearch(IDText.getText());
			String s = new String();
			if (i >= 0) {
				if (system.getCustomers().get(i).getCart().size() > 0) {
					Customer customer = system.getCustomers().get(i);
					s = "";
					for (int j = 0; j < customer.getCart().size(); j++) {
						s += "Code: " + customer.getCart().get(j) + "\n";
						s += "Title: " + getTitle(customer.getCart().get(j)) + "\n**************************\n";
					}
				} else
					s = "The cart is empty.";

			} else
				s = "This id is NOT exist in system.";
			info.setText(s);
		});
		Label cartLabel = new Label("The Cart:");
		cartLabel.setTextFill(Color.WHITE);

		cartPane.add(cartLabel, 0, 2);
		cartPane.add(info, 1, 2);

		ImageView backIcone = new ImageView("https://img.icons8.com/color/344/return.png");
		backIcone.setFitWidth(35);
		backIcone.setFitHeight(35);
		Button back = new Button("Back", backIcone);
		back.setStyle(
				"-fx-color:lightblue;-fx-background-radius: 50; -fx-font-family: 'Comic Sans MS';-fx-font-size: 20px;");

		VBox box = new VBox(20, cartPane, back);
		box.setAlignment(Pos.CENTER);

		back.setOnAction(e -> {
			rentInterface(scene);

		});

		box.setBackground(new Background(new BackgroundImage(new Image("https://wallpaperaccess.com/full/2591633.jpg"),
				null, null, null, null)));

		scene.setRoot(box);
	}

	private String getTitle(String string) {
		int i = binaryMediaSearch(string);
		return system.getMedia().get(i).getTitle();
	}

	public void addToCartAndProcces(Scene scene) {
		GridPane addPane = new GridPane();
		addPane.setAlignment(Pos.CENTER);
		addPane.setHgap(50);
		addPane.setVgap(20);

		Label IDLable = new Label("Customer ID:");
		IDLable.setTextFill(Color.WHITE);
		TextField IDText = new TextField();
		addPane.add(IDLable, 0, 0);
		addPane.add(IDText, 1, 0);
		TextArea customerInfo = new TextArea();
		customerInfo.setEditable(false);
		addPane.add(customerInfo, 1, 1);
		customerInfo.setMinHeight(50);

		HBox customarAndMedia = new HBox(30, addPane);
		customarAndMedia.setAlignment(Pos.CENTER);
		Label dateLable = new Label("Rented Date:");
		dateLable.setTextFill(Color.WHITE);
		TextField dateText = new TextField();

		HBox time = new HBox(10, dateLable, dateText);
		time.setAlignment(Pos.CENTER);

		Button add = new Button("add to cart");
		add.setDisable(true);
		add.setStyle(
				"-fx-color:lightblue;-fx-background-radius: 50; -fx-font-family: 'Comic Sans MS';-fx-font-size: 20px;");

		Label codeLable = new Label("Media Code:");
		codeLable.setTextFill(Color.WHITE);
		TextField codeText = new TextField();
		addPane.add(codeLable, 0, 2);
		addPane.add(codeText, 1, 2);

		TextArea mediaInfo = new TextArea();
		mediaInfo.setMinHeight(50);
		mediaInfo.setEditable(false);
		addPane.add(mediaInfo, 1, 3);

		IDText.setOnKeyTyped(e -> {
			i = binaryCustomerSearch(IDText.getText());
			if (i >= 0) {
				Customer c = system.getCustomers().get(i);
				if (c.getPlan().equals("LIMITED"))
					s = new String("Name of Customer: " + c.getName() + "\n" + "The customer can recive " + c.getValue()
							+ " media and he has " + c.getReceived().size() + " recived.");
				else
					s = new String("name: " + c.getName() + "\n" + "The customer can recived infinit media.");
			} else {
				s = "This ID is NOT exist i system";
				i = -1;
			}
			if (j >= 0 && i >= 0 && (system.getCustomers().get(i).getValue()
					- system.getCustomers().get(i).getReceived().size()) > 0) {
				add.setDisable(false);
				add.setOnAction(e1 -> {
					system.addToCart(IDText.getText(), codeText.getText());
					print();
				});
			} else
				add.setDisable(true);
			customerInfo.setText(s);
		});

		codeText.setOnKeyTyped(e -> {
			j = binaryMediaSearch(codeText.getText());
			if (j >= 0) {
				Media m = system.getMedia().get(j);
				if (m.getNumOfCopies() > 0) {
					s = "Title of media is: " + m.getTitle() + "\n" + "There are " + m.getNumOfCopies()
							+ " in the system";
				} else {
					s = "Title of media is: " + m.getTitle() + "\n" + "There are NO copies of it.";
					j = -1;
				}
			} else {
				s = "This code is NOT exist in system";
			}
			if (j >= 0 && i >= 0 && (system.getCustomers().get(i).getValue()
					- system.getCustomers().get(i).getReceived().size()) > 0) {
				add.setDisable(false);
				add.setOnAction(e1 -> {
					system.addToCart(IDText.getText(), codeText.getText());
					print();
				});
			} else
				add.setDisable(true);
			mediaInfo.setText(s);
		});

		Button processCarts = new Button("process all users carts");
		processCarts.setStyle(
				"-fx-color:lightblue;-fx-background-radius: 50; -fx-font-family: 'Comic Sans MS';-fx-font-size: 20px;");

		processCarts.setOnAction(e -> {

			try {
				FileWriter processFile = new FileWriter(new File("process.txt"), true);
				PrintWriter processFileWriter = new PrintWriter(processFile);
				GregorianCalendar d = new GregorianCalendar();
				dateText.setText(d.get(GregorianCalendar.DAY_OF_MONTH) + " - " + (d.get(GregorianCalendar.MONTH) + 1)
						+ " - " + d.get(GregorianCalendar.YEAR));
				processFileWriter.println(system.processRequests());
				processFileWriter.println("************* Date Of Process *************");
				processFileWriter.println(dateText.getText());
				processFileWriter.println("-------------------------------------------");
				processFileWriter.close();
				print();
			} catch (IOException e1) {
				error.setContentText("You can't save in file !!");
				error.show();
			}
		});

		Button processCart = new Button("process the customer cart");
		processCart.setStyle(
				"-fx-color:lightblue;-fx-background-radius: 50; -fx-font-family: 'Comic Sans MS';-fx-font-size: 20px;");

		processCart.setOnAction(e -> {

			try {
				FileWriter processFile = new FileWriter(new File("process.txt"), true);
				PrintWriter processFileWriter = new PrintWriter(processFile);
				GregorianCalendar d = new GregorianCalendar();
				dateText.setText(d.get(GregorianCalendar.DAY_OF_MONTH) + " - " + (d.get(GregorianCalendar.MONTH) + 1)
						+ " - " + d.get(GregorianCalendar.YEAR));
				processFileWriter.println(system.processRequests(IDText.getText()));
				processFileWriter.println("************* Date Of Process *************");
				processFileWriter.println(dateText.getText());
				processFileWriter.println("-------------------------------------------");
				processFileWriter.close();
				print();
			} catch (IOException e1) {
				error.setContentText("You can't save in file !!");
				error.show();
			}
		});

		Button back = new Button("Back");
		back.setStyle(
				"-fx-color:lightblue;-fx-background-radius: 50; -fx-font-family: 'Comic Sans MS';-fx-font-size: 20px;");

		HBox control = new HBox(20, add, processCart, processCarts, back);
		control.setAlignment(Pos.CENTER);

		back.setOnAction(e -> {
			rentInterface(scene);

		});

		VBox all = new VBox(20, customarAndMedia, time, control);
		all.setAlignment(Pos.CENTER);
		all.setBackground(new Background(new BackgroundImage(new Image("https://wallpaperaccess.com/full/2591633.jpg"),
				null, null, null, null)));
		scene.setRoot(all);

	}

	private void readFromFile() throws IOException {
		File mediaFile = new File("Media.txt");
		File customersFile = new File("Customers.txt");
		Scanner scanMediums = new Scanner(mediaFile);
		Scanner scanCustomers = new Scanner(customersFile);
		ArrayList<Customer> customers = new ArrayList<>();
		ArrayList<Media> media = new ArrayList<>();
		if ((!scanMediums.hasNext() || !mediaFile.exists()) && (!scanCustomers.hasNext() || !customersFile.exists())) {
			scanMediums.close();
			scanCustomers.close();
			warning.setContentText("You don't have any customer or media");
			warning.show();
			return;
		} else if (!scanCustomers.hasNext() || !customersFile.exists()) {
			scanCustomers.close();
			media = readMedia(scanMediums);
			warning.setContentText("You don't have any customer");
			warning.show();
		} else if (!scanMediums.hasNext() || !mediaFile.exists()) {
			scanMediums.close();
			customers = readCustomers(scanCustomers);
			warning.setContentText("You don't have any media");
			warning.show();
		} else {
			customers = readCustomers(scanCustomers);
			media = readMedia(scanMediums);
		}

		system = new MediaRentalManager(customers, media);
		scanMediums.close();
		scanCustomers.close();
	}

	private ArrayList<Customer> readCustomers(Scanner scanCustomers) {
		ArrayList<Customer> customers = new ArrayList<>();
		int j = 0;
		while (scanCustomers.hasNext())
			try {
				String s = scanCustomers.nextLine();
				String[] arr = s.split("[:|]");
				standardForm(arr);

				customers.add(new Customer(standardForm(arr[2]), standardForm(arr[4]), standardForm(arr[6]), arr[8],
						arr[10]));
				if (customers.get(j).getPlan().equalsIgnoreCase("LIMITED"))
					customers.get(j).setValue(Integer.parseInt(arr[16]));

				String[] cart = arr[12].split("[\\[\\],]");
				for (int k = 1; k < cart.length; k++)
					customers.get(j).addToCart(standardForm(cart[k].trim()));
				String[] received = arr[14].split("[\\[\\],]");
				for (int k = 1; k < received.length; k++)
					customers.get(j).addToReceived(standardForm(received[k].trim()));
				j++;
			} catch (RuntimeException e) {
				error.setContentText("there are an error in your file");
				error.show();

			}

		return customers;
	}

	private ArrayList<Media> readMedia(Scanner scanMediums) {
		ArrayList<Media> media = new ArrayList<>();
		while (scanMediums.hasNext())
			try {
				String s = scanMediums.nextLine();
				String[] arr = s.split("[:|]");

				standardForm(arr);

				if (arr[0].equalsIgnoreCase("Movie"))
					media.add(new Movie(standardForm(arr[2]), arr[4], Integer.parseInt(arr[6]), arr[8]));

				else if (arr[0].equalsIgnoreCase("Game"))
					media.add(new Game(standardForm(arr[2]), standardForm(arr[4]), Integer.parseInt(arr[6]),
							Double.parseDouble(arr[8])));
				else if (arr[0].equalsIgnoreCase("Album"))
					media.add(new Album(standardForm(arr[2]), standardForm(arr[4]), Integer.parseInt(arr[6]),
							standardForm(arr[8]), arr[10]));
			} catch (RuntimeException e) {
				error.setContentText("There are an error in the file.");
				error.show();
			}
		return media;
	}

	private void print() {
		try {
			PrintWriter customers = new PrintWriter("Customers.txt");
			PrintWriter media = new PrintWriter("Media.txt");
			customers.println(system.getAllCustomersInfo());
			media.println(system.getAllMediaInfo());
			customers.close();
			media.close();
		} catch (Exception e) {
			error.setContentText("there are an error in your file");
			error.show();
		}

	}

	private String standardForm(String str) {
		str = (str.trim()).toLowerCase();
		String[] arr = str.split("[ ]");
		String ret = new String();
		for (int i = 0; i < arr.length; i++) {
			char[] chars = arr[i].toCharArray();
			chars[0] = Character.toUpperCase(chars[0]);
			String world = new String(chars);
			ret += " " + world;
		}
		return ret.trim();
	}

	private void standardForm(String[] arr) {
		for (int i = 0; i < arr.length; i++)
			arr[i] = arr[i].trim();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
