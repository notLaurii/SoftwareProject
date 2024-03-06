package com.mygdx.game.management;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.mygdx.game.entities.EntityData;
import com.mygdx.game.entities.Player;

import java.util.ArrayList;


//Speichert Spielstände und Stats des Spielers
public class GameSaver {
    private int level;
    private GameManager gameManager;
    private boolean saved=true;
    private String fileFromPath;

    public GameSaver(int level, GameManager gameManager) {
        this.gameManager=gameManager;
    }

    public void update(float deltaTime) {
    }

    public void savePlayerData(Player player) {
        String fileToPath="playerData.json";
        ArrayList<EntityData> dataList = readEntityDataFromFile(fileToPath);
        // Durchlaufe die Liste und suche nach dem Spieler
        for (EntityData data : dataList) {
            if (data.getId() == player.getId()) {
                data.setSkin(player.getSkin());
                data.setJumpVelocity(player.getJumpVelocity());
                data.setMaxHealth(player.getMaxHealth());
                data.setWeaponID(player.getWeaponID());
                data.setAttackDamage(player.getAttackDamage());
                break;
            }
        }

        writeEntityDataToFile(fileToPath, dataList);
    }
    public void saveGameProgress(Player player) {
        String fileToPath="gameProgress.json";
        ArrayList<GameProgress> dataList = readGameProgressFromFile(fileToPath);
        // Durchlaufe die Liste und suche nach dem Spieler
        for (GameProgress data : dataList) {
            if (data.getPlayerId() == player.getId()) {
                data.setLevel(level);
                //data.setAbilitiesUnlocked();
                data.setUnlockedWeapons(gameManager.getUnlockedWeapons());
                data.setUnlockedSkins(gameManager.getUnlockedSkins());
                data.setLevel(gameManager.getLevel());
                break;
            }
        }

        writeGameProgressToFile(fileToPath, dataList);
    }

    private static ArrayList<EntityData> readEntityDataFromFile(String filePath) {
        FileHandle file = Gdx.files.local(filePath);
        if(!file.exists()) {
            file=Gdx.files.internal("defaultPlayerData.json");
        }
        String jsonData = file.readString();
        // Verwende eine Liste, um alle Daten zu speichern
        return new Json().fromJson(ArrayList.class, EntityData.class, jsonData);
    }

    private static ArrayList<GameProgress> readGameProgressFromFile(String filePath) {
        FileHandle file = Gdx.files.local(filePath);
        if(!file.exists()) {
            file=Gdx.files.internal("defaultGameProgress.json");
        }
        String jsonData = file.readString();
        // Verwende eine Liste, um alle Daten zu speichern
        return new Json().fromJson(ArrayList.class, GameProgress.class, jsonData);
    }


    private static void writeEntityDataToFile(String filePath, ArrayList<EntityData> dataList) {
        Json json = new Json();
        json.setUsePrototypes(false);
        json.setTypeName(null);
        json.setOutputType(JsonWriter.OutputType.json);

        // Verwende prettyPrint, um die Ausgabe zu formatieren
        String jsonData = json.prettyPrint(dataList);

        // Entferne zusätzliche Leerzeichen
        jsonData = jsonData.replace("\n", "").replace(" ", "");
        // Schreibe die Daten in die Datei
        FileHandle file = Gdx.files.local(filePath);
        file.writeString(jsonData, false);
    }
    private static void writeGameProgressToFile(String filePath, ArrayList<GameProgress> dataList) {
        Json json = new Json();
        json.setUsePrototypes(false);
        json.setTypeName(null);
        json.setOutputType(JsonWriter.OutputType.json);

        // Verwende prettyPrint, um die Ausgabe zu formatieren
        String jsonData = json.prettyPrint(dataList);

        // Entferne zusätzliche Leerzeichen
        jsonData = jsonData.replace("\n", "").replace(" ", "");
        // Schreibe die Daten in die Datei
        FileHandle file = Gdx.files.local(filePath);
        file.writeString(jsonData, false);
    }
    public void setSaved(boolean value) {
        this.saved=value;
    }
}