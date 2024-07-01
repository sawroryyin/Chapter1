package se233.chapter1.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.StackPane;
import se233.chapter1.Launcher;
import se233.chapter1.model.DamageType;
import se233.chapter1.model.character.BasedCharacter;
import se233.chapter1.model.item.Armor;
import se233.chapter1.model.item.BasedEquipment;
import se233.chapter1.model.item.Weapon;



import java.util.ArrayList;

public class AllCustomHandler {
    public static class GenCharacterHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            Launcher.setMainCharacter(GenCharacter.setUpCharacter());
            Launcher.refreshPane();
        }
    }

    public static boolean equipSuccess;

    public static class UnEquipItemHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            Launcher.getMainCharacter().equipArmor(null);
            Launcher.getMainCharacter().equipWeapon(null);
            Launcher.setEquippedArmor(null);
            Launcher.setEquippedWeapon(null);

            Launcher.setAllEquipments(GenItemList.setUpItemList());
            Launcher.refreshPane();
        }
    }

    public static void onDragDetected(MouseEvent event, BasedEquipment equipment, ImageView imgView) {
        Dragboard db = imgView.startDragAndDrop(TransferMode.ANY);
        db.setDragView(imgView.getImage());
        ClipboardContent content = new ClipboardContent();
        content.put(BasedEquipment.DATA_FORMAT, equipment);
        db.setContent(content);
        event.consume();
    }

    public static void onDragOver(DragEvent event, String type) {
        Dragboard dragboard = event.getDragboard();
        BasedCharacter character = Launcher.getMainCharacter();
        BasedEquipment retrievedEquipment = (BasedEquipment)dragboard.getContent(BasedEquipment.DATA_FORMAT);

        String retrievedEquipmentName = retrievedEquipment.getClass().getSimpleName();

        if(dragboard.hasContent(BasedEquipment.DATA_FORMAT) && retrievedEquipmentName.equals(type)){

            Weapon carriedWeapon=null;
            if(retrievedEquipmentName.equals("Weapon")){carriedWeapon=(Weapon)retrievedEquipment;}

            switch (character.getName()){
                case("MagicChar1"):
                    if((retrievedEquipmentName.equals("Weapon")&& carriedWeapon.getDamageType().equals(DamageType.magical)) || retrievedEquipmentName.equals("Armor")){
                    event.acceptTransferModes(TransferMode.MOVE);
                }
                break;
                case("PhysicalChar1"):
                    if((retrievedEquipmentName.equals("Weapon")&& carriedWeapon.getDamageType().equals(DamageType.physical)) || retrievedEquipmentName.equals("Armor")){
                    event.acceptTransferModes(TransferMode.MOVE);
                }
                break;
                case("MagicChar2"):
                    if(retrievedEquipmentName.equals("Weapon")){
                        event.acceptTransferModes(TransferMode.MOVE);
                    }
                    break;
            }
        }
    }

    public static void onDragDropped(DragEvent event, Label lbl, StackPane imgGroup) {
        boolean dragCompleted = false;
        Dragboard dragboard = event.getDragboard();
        ArrayList<BasedEquipment> allEquipments = Launcher.getAllEquipments();
        if (dragboard.hasContent(BasedEquipment.DATA_FORMAT)) {
            BasedEquipment retrievedEquipment = (BasedEquipment)dragboard.getContent(
                    BasedEquipment.DATA_FORMAT);
            BasedCharacter character = Launcher.getMainCharacter();

            if (retrievedEquipment.getClass().getSimpleName().equals("Weapon")){
                if (Launcher.getEquippedWeapon() != null)
                    allEquipments.add(Launcher.getEquippedWeapon());
                Launcher.setEquippedWeapon((Weapon) retrievedEquipment);
                character.equipWeapon((Weapon) retrievedEquipment);
            } else {
                if (Launcher.getEquippedArmor() !=null)
                    allEquipments.add(Launcher.getEquippedArmor());
                Launcher.setEquippedArmor((Armor) retrievedEquipment);
                character.equipArmor((Armor) retrievedEquipment);
            }
            Launcher.setMainCharacter(character);
            Launcher.setAllEquipments(allEquipments);
            Launcher.refreshPane();

            ImageView imgView = new ImageView();
            if (imgGroup.getChildren().size() != 1) {
                imgGroup.getChildren().remove(1);
                Launcher.refreshPane();
            }
            lbl.setText(retrievedEquipment.getClass().getSimpleName() + ":\n" + retrievedEquipment.getName());
            imgView.setImage(new Image(Launcher.class.getResource(retrievedEquipment.getImagepath()).toString()));
            imgGroup.getChildren().add(imgView);
            dragCompleted = true;
            equipSuccess = true;
        }
        event.setDropCompleted(dragCompleted);
    }

    public static void onEquipDone(DragEvent event) {
        Dragboard dragboard = event.getDragboard();
        ArrayList<BasedEquipment> allEquipments = Launcher.getAllEquipments();
        BasedEquipment retrievedEquipment = (BasedEquipment)dragboard.getContent(
                BasedEquipment.DATA_FORMAT);
        if(equipSuccess) {
            int pos =-1;
            for(int i=0; i<allEquipments.size() ; i++) {
                if (allEquipments.get(i).getName().equals(retrievedEquipment.getName())) {
                    pos = i;
                }
            }
            if (pos !=-1) {
                allEquipments.remove(pos);
            }
        }

        equipSuccess = false;
        Launcher.setAllEquipments(allEquipments);
        Launcher.refreshPane();
    }
}
