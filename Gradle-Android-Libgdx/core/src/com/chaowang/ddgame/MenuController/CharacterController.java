package com.chaowang.ddgame.MenuController;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.chaowang.ddgame.CharacterModel.Abilities;
import com.chaowang.ddgame.View.MainMenuScreen;
import com.chaowang.ddgame.PublicParameter;

import com.chaowang.ddgame.CharacterModel.Character;
import com.chaowang.ddgame.util.CharacterScoreModifier;
import com.chaowang.ddgame.util.Dice;
import com.chaowang.ddgame.View.CharacterEditorScreen;

import java.util.Arrays;
import java.util.Collections;

/**
 * controller for character
 * @author chao wang
 * @version 1.0
 */
public class CharacterController {
	Character character;
	CharacterEditorScreen view;
	/**
	 * constructor
	 * @param view
	 * @param model
	 */
	public CharacterController(CharacterEditorScreen view, Character model) {
		this.character = model;
		this.view = view;
	}
	/**
	 * get character
	 * @return character
	 */
	public Character getCharacter() {
		return character;
	}
	/**
	 * set character
	 * @param character
	 */
	public void setCharacter(Character character) {
		this.character = character;
	}
    /**
     * controller for switching to previous race information after pressing button 
     */
	public void controlRaceLeftButton() {
		character.previousRace();
		view.raceLabel.setText(character.getRaceType().toString());
		view.characterImage.setDrawable(new SpriteDrawable(new Sprite(character.getTexture())));
	}
    /**
     * controller for switching to the next race information after pressing  button 
     */
	public void controlRaceRightButotn() {
		character.nextRace();
		view.raceLabel.setText(character.getRaceType().toString());
		view.characterImage.setDrawable(new SpriteDrawable(new Sprite(character.getTexture())));
	}

	/**
	 * controller for switching to previous race information after pressing button
	 */
	public void controlFighterLeftButton() {
		if(character.previousFighterType()){
			Integer[] arrTmp = new Integer[Abilities.ABILITYSIZE];
			for ( int i = 0 ; i < arrTmp.length; i++){
				arrTmp[i] = character.getAbilities().getAbilityArr()[i];
			}
			sortAbilityArrToDisplay(arrTmp);
		}
		view.fighterTypeLabel.setText(character.getFighterType().toString());
	}
	/**
	 * controller for switching to the next race information after pressing  button
	 */
	public void controlFighterRightButotn() {
		if(character.nextFighterType()){
			Integer[] arrTmp = new Integer[Abilities.ABILITYSIZE];
			for ( int i = 0 ; i < arrTmp.length; i++){
				arrTmp[i] = character.getAbilities().getAbilityArr()[i];
			}
			sortAbilityArrToDisplay(arrTmp);
		}
		view.fighterTypeLabel.setText(character.getFighterType().toString());
	}

	/**
     * controller for switching to main menu page
     */
	public void controlSwitchPageButton(){
		character.setLevel(Integer.parseInt(view.levelLabel.getText().toString()));
		character.setName(view.nameText.getText());
		for (int i = 0; i < view.bonusField.length; i++) {
			character.getAbilityBonusArr()[i] = Integer.parseInt(view.bonusField[i].getText());
		}
	}
    /**
     * controller for switching to the previous level information
     */
	public void controlLevelLeftButton() {
		character.resetPromotePoint();
		character.levelDown();
		view.levelLabel.setText(Integer.toString(character.getLevel()));
		view.promotePointLabel.setText(Integer.toString(character.getPromotionPoint()));
		view.confirmButton.setTouchable(Touchable.enabled);
		for (int i = 0; i < view.bonusField.length; i++) {
			view.bonusField[i].setText(Integer.toString(character.getAbilityBonusArr()[i]));
			view.bonusField[i].setDisabled(false);
		}
	}
    /**
     * controller for switching to the next race information
     */
	public void controlLevelRightButton() {
		character.resetPromotePoint();
		character.levelUp();
		view.levelLabel.setText(Integer.toString(character.getLevel()));
		view.promotePointLabel.setText(Integer.toString(character.getPromotionPoint()));
		view.confirmButton.setTouchable(Touchable.enabled);
		for (int i = 0; i < view.bonusField.length; i++) {
			view.bonusField[i].setText(Integer.toString(character.getAbilityBonusArr()[i]));
			view.bonusField[i].setDisabled(false);
		}
	}
	/**
	 * controller for confirming all input information
	 */
	public void controlConfirmButton() {
		if (view.bonusField[0].getText().matches(("^[0-9]$")) &&
				view.bonusField[1].getText().matches(("^[0-9]$")) &&
				view.bonusField[2].getText().matches(("^[0-9]$")) &&
				view.bonusField[3].getText().matches(("^[0-9]$")) &&
				view.bonusField[4].getText().matches(("^[0-9]$")) &&
				view.bonusField[5].getText().matches(("^[0-9]$"))) {
			int sum = 0;
			for (int i = 0; i < view.bonusField.length; i++) {
				sum += Integer.valueOf(view.bonusField[i].getText());
			}
			if (sum <= character.getPromotionPoint()) {
				character.setPromotionPoint(character.getPromotionPoint() - sum);
				for (int i = 0; i < view.bonusField.length; i++) {
					character.getAbilityBonusArr()[i] = Integer.valueOf(view.bonusField[i].getText());
					view.bonusField[i].setDisabled(true);
				}
				character.setHitPoints(CharacterScoreModifier.hitPointCalculator(character.getConstitution() + character.getConstitutionBonus(), character.getLevel()));
				character.setArmorClass(CharacterScoreModifier.armorClassCalculator(character.getDexterity() + character.getDexterityBonus()));
				character.setAttackBonus(CharacterScoreModifier.attachBonusCalculator(character.getStrength() + character.getStrengthBonus(),
						character.getDexterity() + character.getDexterityBonus(), character.getLevel()));
				character.setDamageBonus(CharacterScoreModifier.damageBonusCalculator(character.getStrength() + character.getStrengthBonus()));
				view.promotePointLabel.setText(Integer.toString(character.getPromotionPoint()));
				view.hitpointLabel.setText(Integer.toString(character.getHitPoints()));
				view.armorClassLabel.setText(Integer.toString(character.getArmorClass()));
				view.attackBonusLabel.setText(Integer.toString(character.getAttackBonus()));
				view.damageBonusLaber.setText(Integer.toString(character.getDamageBonus()));
				view.levelLeftButton.setTouchable(Touchable.disabled);
				view.levelRightButton.setTouchable(Touchable.disabled);
				view.confirmButton.setTouchable(Touchable.disabled);
			} else {
				new Dialog("Error", MainMenuScreen.skin, "dialog") {
				}.text("Input greater than promo point").button("OK", true).key(Input.Keys.ENTER, true).show(view.stage);
			}
		} else {
			new Dialog("Error", MainMenuScreen.skin, "dialog") {
			}.text("Input too large").button("OK", true).key(Input.Keys.ENTER, true).show(view.stage);
		}
	}
	/**
	 * controller for saving information after pressing save button
	 */
	public void controlSaveButton() {
		if (view.levelLabel.getText().toString().matches("^[1-9]$|^0[1-9]$|^1[0]$|^") && Integer.parseInt(view.wisdomLabel.getText().toString()) != 0) {
			if (view.bonusField[0].isDisabled()) {
				character.setLevel(Integer.parseInt(view.levelLabel.getText().toString()));
				character.setName(view.nameText.getText());
				MainMenuScreen.characterInventory.addToInventory(character);
				MainMenuScreen.characterInventory.saveToFile();
				view.inventoryTable.clearChildren();
				buildInventoryMatrix();
				addInventoryMatrixListener();
				character = new Character();
				view.setCharacter(character);
				initialEditorItem();
				view.confirmButton.setTouchable(Touchable.disabled);
			} else {
				new Dialog("Error", MainMenuScreen.skin, "dialog") {
				}.text("Need to click Ok button first").button("OK", true).key(Input.Keys.ENTER, true)
						.show(view.stage);
			}
		} else {
			new Dialog("Error", MainMenuScreen.skin, "dialog") {
			}.text("Character input value error").button("OK", true).key(Input.Keys.ENTER, true)
					.show(view.stage);
		}
	}
	/**
	 * control to dice
	 */
	public void controlDiceButton() {
		if (view.levelLabel.getText().toString().matches("^[1-9]$|^0[1-9]$|^1[1-9]$|^2[0]$|^") && Integer.valueOf(view.wisdomLabel.getText().toString()) == 0) {
			character.setLevel(Integer.parseInt(view.levelLabel.getText().toString()));
			Integer[] arrTmp = new Integer[Abilities.ABILITYSIZE];
			for ( int i = 0 ; i < arrTmp.length; i++){
				arrTmp[i] = Dice.roll(Dice.DICENUMBER, Dice.DICESIDE );
			}
			sortAbilityArrToDisplay(arrTmp);
		}
	}

	private void sortAbilityArrToDisplay(Integer[] arrTmp) {
		Arrays.sort(arrTmp, Collections.reverseOrder());
		for ( int i = 0 ; i < arrTmp.length; i++ ){
            character.getAbilities().getAbilityArr()[character.getFighterType().getAbilityImportance()[i]] = arrTmp[i];
        }
		view.strengthLabel.setText(Integer.toString(character.getStrength()));
		view.dexterityLabel.setText(Integer.toString(character.getDexterity()));
		view.constitutionLabel.setText(Integer.toString(character.getConstitution()));
		view.wisdomLabel.setText(Integer.toString(character.getWisdom()));
		view.intellegenceLabel.setText(Integer.toString(character.getIntelligence()));
		view.charismaLabel.setText(Integer.toString(character.getCharisma()));

		if(character.getStrength() != 0){
			character.setHitPoints(CharacterScoreModifier.hitPointCalculator(character.getConstitution(), character.getLevel()));
			character.setArmorClass(CharacterScoreModifier.armorClassCalculator(character.getDexterity()));
			character.setAttackBonus(CharacterScoreModifier.attachBonusCalculator(character.getStrength(), character.getDexterity(), character.getLevel()));
			character.setDamageBonus(CharacterScoreModifier.damageBonusCalculator(character.getStrength()));

			view.hitpointLabel.setText(Integer.toString(character.getHitPoints()));
			view.armorClassLabel.setText(Integer.toString(character.getArmorClass()));
			view.attackBonusLabel.setText(Integer.toString(character.getAttackBonus()));
			view.damageBonusLaber.setText(Integer.toString(character.getDamageBonus()));
		}
	}

	/**
	 * listen any change from character inventory
	 */
	public void addInventoryMatrixListener() {
		for (int i = 0; i < MainMenuScreen.characterInventory.getChatacterPack().size; i++) {
			view.inventoryMatrix[i].addListener(new ClickListener(i) {
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					character = new Character(
							MainMenuScreen.characterInventory.getChatacterPack().get(getButton()).getName(),
							MainMenuScreen.characterInventory.getChatacterPack().get(getButton()).getLevel(),
							MainMenuScreen.characterInventory.getChatacterPack().get(getButton()).getPromotionPoint(),
							MainMenuScreen.characterInventory.getChatacterPack().get(getButton()).getRaceType(),
							MainMenuScreen.characterInventory.getChatacterPack().get(getButton()).getFighterType(),
							MainMenuScreen.characterInventory.getChatacterPack().get(getButton()).getBaseAttributes(),
							MainMenuScreen.characterInventory.getChatacterPack().get(getButton()).getAbilityBonusArr(),
							MainMenuScreen.characterInventory.getChatacterPack().get(getButton()).getBackpack(),
							MainMenuScreen.characterInventory.getChatacterPack().get(getButton()).getEquipment());
					view.setCharacter(character);
					initialEditorItem();
					MainMenuScreen.characterInventory.getChatacterPack().removeIndex(getButton());
					MainMenuScreen.characterInventory.saveToFile();
					view.inventoryTable.clearChildren();
					buildInventoryMatrix();
					addInventoryMatrixListener();
					view.levelLeftButton.setTouchable(Touchable.enabled);
					view.levelRightButton.setTouchable(Touchable.enabled);
					return true;
				}

				@Override
				public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
					view.characterInfoLabel.setText(MainMenuScreen.characterInventory.getChatacterPack().get(getButton()).toString());
				}

				@Override
				public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
					view.characterInfoLabel.setText("");
				}
			});
		}
	}

	/**
	 * build matrix structure for inventory
	 */
	public void buildInventoryMatrix() {
		for (int i = 0; i < PublicParameter.CHARACTER_INVENTORY_ROW; i++) {
			for (int j = 0; j < PublicParameter.CHARACTER_INVENTORY_COLUMN; j++) {
				if ((i * PublicParameter.CHARACTER_INVENTORY_COLUMN) + j < MainMenuScreen.characterInventory.getChatacterPack().size) {
					view.inventoryMatrix[(i * PublicParameter.CHARACTER_INVENTORY_COLUMN) + j] = new ImageButton(new TextureRegionDrawable(new TextureRegion(MainMenuScreen.characterInventory.getChatacterPack().get(i * PublicParameter.CHARACTER_INVENTORY_COLUMN + j).getTexture())));
				} else {
					view.inventoryMatrix[(i * PublicParameter.CHARACTER_INVENTORY_COLUMN) + j] = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("items/unknown.png")))));
				}
				ImageButton tempButton = view.inventoryMatrix[(i * PublicParameter.CHARACTER_INVENTORY_COLUMN) + j];
				view.inventoryTable.add(tempButton).width(PublicParameter.CHARACTER_CELL_WIDTH).height(PublicParameter.CHARACTER_CELL_HEIGHT).space(15);
			}
			view.inventoryTable.row();
		}
	}
	/**
	 * set up initial character editor page
	 */
	public void initialEditorItem() {
		view.raceLabel.setText(character.getRaceType().toString());
		view.characterImage.setDrawable(new SpriteDrawable(new Sprite(character.getTexture())));
		view.levelLabel.setText(Integer.toString(character.getLevel()));
		view.promotePointLabel.setText(Integer.toString(character.getPromotionPoint()));
		view.nameText.setText(character.getName());
		view.strengthLabel.setText(Integer.toString(character.getStrength()));
		view.dexterityLabel.setText(Integer.toString(character.getDexterity()));
		view.constitutionLabel.setText(Integer.toString(character.getConstitution()));
		view.wisdomLabel.setText(Integer.toString(character.getWisdom()));
		view.intellegenceLabel.setText(Integer.toString(character.getIntelligence()));
		view.charismaLabel.setText(Integer.toString(character.getCharisma()));
		view.hitpointLabel.setText(Integer.toString(character.getHitPoints()));
		view.armorClassLabel.setText(Integer.toString(character.getArmorClass()));
		view.attackBonusLabel.setText(Integer.toString(character.getAttackBonus()));
		view.damageBonusLaber.setText(Integer.toString(character.getDamageBonus()));
		for (int i = 0; i < view.bonusField.length; i++) {
			view.bonusField[i].setText(Integer.toString(character.getAbilityBonusArr()[i]));
		}

	}
}

