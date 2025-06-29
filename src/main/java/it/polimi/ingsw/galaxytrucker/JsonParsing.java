package it.polimi.ingsw.galaxytrucker;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.galaxytrucker.Model.Color;
import it.polimi.ingsw.galaxytrucker.Model.Tiles.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class JsonTiles{
    public String connector;
    public String cannon;
    public String shield;
    public String cabin;
    public String engine;
    public String bioadaptor;
    public String cargo;
    public String battery;

    /**
     * This is auxiliary class that stores Json Strings,is used by JsonParsing to build Tiles
     */
    public JsonTiles(){
        engine ="{\"engine1\":{\n" +
                "    \"north\":\"DOUBLE\",\n" +
                "    \"west\":\"NONE\",\n" +
                "    \"south\":\"ENGINE_SINGLE\",\n" +
                "    \"east\":\"NONE\",\n" +
                "    \"doublePower\":false,\n" +
                "    \"activeState\":true\n" +
                "    },\n" +
                "    \"engine2\":{\n" +
                "    \"north\":\"UNIVERSAL\",\n" +
                "    \"west\":\"NONE\",\n" +
                "    \"south\":\"ENGINE_SINGLE\",\n" +
                "    \"east\":\"DOUBLE\",\n" +
                "    \"doublePower\":false,\n" +
                "    \"activeState\":true\n" +
                "    },\n" +
                "    \"engine3\":{\n" +
                "    \"north\":\"NONE\",\n" +
                "    \"west\":\"UNIVERSAL\",\n" +
                "    \"south\":\"ENGINE_SINGLE\",\n" +
                "    \"east\":\"NONE\",\n" +
                "    \"doublePower\":false,\n" +
                "    \"activeState\":true\n" +
                "    },\n" +
                "    \"engine4\":{\n" +
                "    \"north\":\"SINGLE\",\n" +
                "    \"west\":\"NONE\",\n" +
                "    \"south\":\"ENGINE_SINGLE\",\n" +
                "    \"east\":\"NONE\",\n" +
                "    \"doublePower\":false,\n" +
                "    \"activeState\":true\n" +
                "    },\n" +
                "    \"engine5\":{\n" +
                "    \"north\":\"DOUBLE\",\n" +
                "    \"west\":\"NONE\",\n" +
                "    \"south\":\"ENGINE_SINGLE\",\n" +
                "    \"east\":\"NONE\",\n" +
                "    \"doublePower\":false,\n" +
                "    \"activeState\":true\n" +
                "    },\n" +
                "    \"engine6\":{\n" +
                "    \"north\":\"DOUBLE\",\n" +
                "    \"west\":\"DOUBLE\",\n" +
                "    \"south\":\"ENGINE_SINGLE\",\n" +
                "    \"east\":\"NONE\",\n" +
                "    \"doublePower\":false,\n" +
                "    \"activeState\":true\n" +
                "    },\n" +
                "    \"engine7\":{\n" +
                "    \"north\":\"NONE\",\n" +
                "    \"west\":\"DOUBLE\",\n" +
                "    \"south\":\"ENGINE_SINGLE\",\n" +
                "    \"east\":\"SINGLE\",\n" +
                "    \"doublePower\":false,\n" +
                "    \"activeState\":true\n" +
                "    },\n" +
                "    \"engine8\":{\n" +
                "    \"north\":\"SINGLE\",\n" +
                "    \"west\":\"DOUBLE\",\n" +
                "    \"south\":\"ENGINE_SINGLE\",\n" +
                "    \"east\":\"DOUBLE\",\n" +
                "    \"doublePower\":false,\n" +
                "    \"activeState\":true\n" +
                "    },\n" +
                "    \"engine9\":{\n" +
                "    \"north\":\"UNIVERSAL\",\n" +
                "    \"west\":\"DOUBLE\",\n" +
                "    \"south\":\"ENGINE_SINGLE\",\n" +
                "    \"east\":\"SINGLE\",\n" +
                "    \"doublePower\":false,\n" +
                "    \"activeState\":true\n" +
                "    },\n" +
                "    \"engine10\":{\n" +
                "    \"north\":\"SINGLE\",\n" +
                "    \"west\":\"UNIVERSAL\",\n" +
                "    \"south\":\"ENGINE_SINGLE\",\n" +
                "    \"east\":\"NONE\",\n" +
                "    \"doublePower\":false,\n" +
                "    \"activeState\":true\n" +
                "    },\n" +
                "    \"engine11\":{\n" +
                "    \"north\":\"NONE\",\n" +
                "    \"west\":\"NONE\",\n" +
                "    \"south\":\"ENGINE_SINGLE\",\n" +
                "    \"east\":\"UNIVERSAL\",\n" +
                "    \"doublePower\":false,\n" +
                "    \"activeState\":true\n" +
                "    },\n" +
                "    \"engine12\":{\n" +
                "    \"north\":\"NONE\",\n" +
                "    \"west\":\"UNIVERSAL\",\n" +
                "    \"south\":\"ENGINE_SINGLE\",\n" +
                "    \"east\":\"DOUBLE\",\n" +
                "    \"doublePower\":false,\n" +
                "    \"activeState\":true\n" +
                "    },\n" +
                "    \"engine13\":{\n" +
                "    \"north\":\"DOUBLE\",\n" +
                "    \"west\":\"NONE\",\n" +
                "    \"south\":\"ENGINE_SINGLE\",\n" +
                "    \"east\":\"UNIVERSAL\",\n" +
                "    \"doublePower\":false,\n" +
                "    \"activeState\":true\n" +
                "    },\n" +
                "    \"engine14\":{\n" +
                "    \"north\":\"UNIVERSAL\",\n" +
                "    \"west\":\"SINGLE\",\n" +
                "    \"south\":\"ENGINE_SINGLE\",\n" +
                "    \"east\":\"NONE\",\n" +
                "    \"doublePower\":false,\n" +
                "    \"activeState\":true\n" +
                "    },\n" +
                "    \"engine15\":{\n" +
                "    \"north\":\"NONE\",\n" +
                "    \"west\":\"SINGLE\",\n" +
                "    \"south\":\"ENGINE_SINGLE\",\n" +
                "    \"east\":\"UNIVERSAL\",\n" +
                "    \"doublePower\":false,\n" +
                "    \"activeState\":true\n" +
                "    },\n" +
                "    \"engine16\":{\n" +
                "    \"north\":\"UNIVERSAL\",\n" +
                "    \"west\":\"SINGLE\",\n" +
                "    \"south\":\"ENGINE_SINGLE\",\n" +
                "    \"east\":\"DOUBLE\",\n" +
                "    \"doublePower\":false,\n" +
                "    \"activeState\":true\n" +
                "    },\n" +
                "    \"engine17\":{\n" +
                "    \"north\":\"NONE\",\n" +
                "    \"west\":\"UNIVERSAL\",\n" +
                "    \"south\":\"ENGINE_SINGLE\",\n" +
                "    \"east\":\"NONE\",\n" +
                "    \"doublePower\":false,\n" +
                "    \"activeState\":true\n" +
                "    },\n" +
                "    \"engine18\":{\n" +
                "    \"north\":\"DOUBLE\",\n" +
                "    \"west\":\"SINGLE\",\n" +
                "    \"south\":\"ENGINE_SINGLE\",\n" +
                "    \"east\":\"SINGLE\",\n" +
                "    \"doublePower\":false,\n" +
                "    \"activeState\":true\n" +
                "    },\n" +
                "    \"engine19\":{\n" +
                "    \"north\":\"SINGLE\",\n" +
                "    \"west\":\"NONE\",\n" +
                "    \"south\":\"ENGINE_SINGLE\",\n" +
                "    \"east\":\"SINGLE\",\n" +
                "    \"doublePower\":false,\n" +
                "    \"activeState\":true\n" +
                "    },\n" +
                "    \"engine20\":{\n" +
                "    \"north\":\"SINGLE\",\n" +
                "    \"west\":\"NONE\",\n" +
                "    \"south\":\"ENGINE_SINGLE\",\n" +
                "    \"east\":\"NONE\",\n" +
                "    \"doublePower\":false,\n" +
                "    \"activeState\":true\n" +
                "    },\n" +
                "    \"engine21\":{\n" +
                "    \"north\":\"NONE\",\n" +
                "    \"west\":\"NONE\",\n" +
                "    \"south\":\"ENGINE_SINGLE\",\n" +
                "    \"east\":\"UNIVERSAL\",\n" +
                "    \"doublePower\":false,\n" +
                "    \"activeState\":true\n" +
                "    },\n" +
                "    \"doubleEngine1\":{\n" +
                "    \"north\":\"SINGLE\",\n" +
                "    \"west\":\"NONE\",\n" +
                "    \"south\":\"ENGINE_DOUBLE\",\n" +
                "    \"east\":\"UNIVERSAL\",\n" +
                "    \"doublePower\":true,\n" +
                "    \"activeState\":false\n" +
                "    },\n" +
                "    \"doubleEngine2\":{\n" +
                "    \"north\":\"UNIVERSAL\",\n" +
                "    \"west\":\"NONE\",\n" +
                "    \"south\":\"ENGINE_DOUBLE\",\n" +
                "    \"east\":\"SINGLE\",\n" +
                "    \"doublePower\":true,\n" +
                "    \"activeState\":false\n" +
                "    },\n" +
                "    \"doubleEngine3\":{\n" +
                "    \"north\":\"DOUBLE\",\n" +
                "    \"west\":\"DOUBLE\",\n" +
                "    \"south\":\"ENGINE_DOUBLE\",\n" +
                "    \"east\":\"DOUBLE\",\n" +
                "    \"doublePower\":true,\n" +
                "    \"activeState\":false\n" +
                "    },\n" +
                "    \"doubleEngine4\":{\n" +
                "    \"north\":\"NONE\",\n" +
                "    \"west\":\"UNIVERSAL\",\n" +
                "    \"south\":\"ENGINE_DOUBLE\",\n" +
                "    \"east\":\"UNIVERSAL\",\n" +
                "    \"doublePower\":true,\n" +
                "    \"activeState\":false\n" +
                "    },\n" +
                "    \"doubleEngine5\":{\n" +
                "    \"north\":\"UNIVERSAL\",\n" +
                "    \"west\":\"DOUBLE\",\n" +
                "    \"south\":\"ENGINE_DOUBLE\",\n" +
                "    \"east\":\"NONE\",\n" +
                "    \"doublePower\":true,\n" +
                "    \"activeState\":false\n" +
                "    },\n" +
                "    \"doubleEngine6\":{\n" +
                "    \"north\":\"DOUBLE\",\n" +
                "    \"west\":\"UNIVERSAL\",\n" +
                "    \"south\":\"ENGINE_DOUBLE\",\n" +
                "    \"east\":\"NONE\",\n" +
                "    \"doublePower\":true,\n" +
                "    \"activeState\":false\n" +
                "    },\n" +
                "    \"doubleEngine7\":{\n" +
                "    \"north\":\"DOUBLE\",\n" +
                "    \"west\":\"NONE\",\n" +
                "    \"south\":\"ENGINE_DOUBLE\",\n" +
                "    \"east\":\"NONE\",\n" +
                "    \"doublePower\":true,\n" +
                "    \"activeState\":false\n" +
                "    },\n" +
                "    \"doubleEngine8\":{\n" +
                "    \"north\":\"SINGLE\",\n" +
                "    \"west\":\"SINGLE\",\n" +
                "    \"south\":\"ENGINE_DOUBLE\",\n" +
                "    \"east\":\"SINGLE\",\n" +
                "    \"doublePower\":true,\n" +
                "    \"activeState\":false\n" +
                "    },\n" +
                "    \"doubleEngine9\":{\n" +
                "    \"north\":\"SINGLE\",\n" +
                "    \"west\":\"NONE\",\n" +
                "    \"south\":\"ENGINE_DOUBLE\",\n" +
                "    \"east\":\"NONE\",\n" +
                "    \"doublePower\":true,\n" +
                "    \"activeState\":false\n" +
                "    }}";
        cargo ="{\"normalStorage1\":{\n" +
                "    \"north\":\"NONE\",\n" +
                "    \"west\":\"UNIVERSAL\",\n" +
                "    \"south\":\"NONE\",\n" +
                "    \"east\":\"NONE\",\n" +
                "    \"slotsNumber\":2,\n" +
                "    \"fitsRed\":false,\n" +
                "    \"tileContent\":[]\n" +
                "    },\n" +
                "    \"normalStorage2\":{\n" +
                "    \"north\":\"UNIVERSAL\",\n" +
                "    \"west\":\"UNIVERSAL\",\n" +
                "    \"south\":\"NONE\",\n" +
                "    \"east\":\"NONE\",\n" +
                "    \"slotsNumber\":2,\n" +
                "    \"fitsRed\":false,\n" +
                "    \"tileContent\":[]\n" +
                "    },\n" +
                "    \"normalStorage3\":{\n" +
                "    \"north\":\"DOUBLE\",\n" +
                "    \"west\":\"NONE\",\n" +
                "    \"south\":\"UNIVERSAL\",\n" +
                "    \"east\":\"SINGLE\",\n" +
                "    \"slotsNumber\":2,\n" +
                "    \"fitsRed\":false,\n" +
                "    \"tileContent\":[]\n" +
                "    },\n" +
                "    \"normalStorage4\":{\n" +
                "    \"north\":\"DOUBLE\",\n" +
                "    \"west\":\"UNIVERSAL\",\n" +
                "    \"south\":\"DOUBLE\",\n" +
                "    \"east\":\"SINGLE\",\n" +
                "    \"slotsNumber\":2,\n" +
                "    \"fitsRed\":false,\n" +
                "    \"tileContent\":[]\n" +
                "    },\n" +
                "    \"normalStorage5\":{\n" +
                "    \"north\":\"NONE\",\n" +
                "    \"west\":\"UNIVERSAL\",\n" +
                "    \"south\":\"NONE\",\n" +
                "    \"east\":\"DOUBLE\",\n" +
                "    \"slotsNumber\":2,\n" +
                "    \"fitsRed\":false,\n" +
                "    \"tileContent\":[]\n" +
                "    },\n" +
                "    \"normalStorage6\":{\n" +
                "    \"north\":\"DOUBLE\",\n" +
                "    \"west\":\"SINGLE\",\n" +
                "    \"south\":\"NONE\",\n" +
                "    \"east\":\"UNIVERSAL\",\n" +
                "    \"slotsNumber\":2,\n" +
                "    \"fitsRed\":false,\n" +
                "    \"tileContent\":[]\n" +
                "    },\n" +
                "    \"normalStorage7\":{\n" +
                "    \"north\":\"DOUBLE\",\n" +
                "    \"west\":\"SINGLE\",\n" +
                "    \"south\":\"UNIVERSAL\",\n" +
                "    \"east\":\"SINGLE\",\n" +
                "    \"slotsNumber\":2,\n" +
                "    \"fitsRed\":false,\n" +
                "    \"tileContent\":[]\n" +
                "    },\n" +
                "    \"normalStorage8\":{\n" +
                "    \"north\":\"NONE\",\n" +
                "    \"west\":\"UNIVERSAL\",\n" +
                "    \"south\":\"NONE\",\n" +
                "    \"east\":\"SINGLE\",\n" +
                "    \"slotsNumber\":2,\n" +
                "    \"fitsRed\":false,\n" +
                "    \"tileContent\":[]\n" +
                "    },\n" +
                "    \"normalStorage9\":{\n" +
                "    \"north\":\"NONE\",\n" +
                "    \"west\":\"UNIVERSAL\",\n" +
                "    \"south\":\"NONE\",\n" +
                "    \"east\":\"NONE\",\n" +
                "    \"slotsNumber\":2,\n" +
                "    \"fitsRed\":false,\n" +
                "    \"tileContent\":[]\n" +
                "    },\n" +
                "    \"normalStorage10\":{\n" +
                "    \"north\":\"DOUBLE\",\n" +
                "    \"west\":\"DOUBLE\",\n" +
                "    \"south\":\"SINGLE\",\n" +
                "    \"east\":\"NONE\",\n" +
                "    \"slotsNumber\":3,\n" +
                "    \"fitsRed\":false,\n" +
                "    \"tileContent\":[]\n" +
                "    },\n" +
                "    \"normalStorage11\":{\n" +
                "    \"north\":\"SINGLE\",\n" +
                "    \"west\":\"NONE\",\n" +
                "    \"south\":\"SINGLE\",\n" +
                "    \"east\":\"NONE\",\n" +
                "    \"slotsNumber\":3,\n" +
                "    \"fitsRed\":false,\n" +
                "    \"tileContent\":[]\n" +
                "    },\n" +
                "    \"normalStorage12\":{\n" +
                "    \"north\":\"NONE\",\n" +
                "    \"west\":\"DOUBLE\",\n" +
                "    \"south\":\"NONE\",\n" +
                "    \"east\":\"NONE\",\n" +
                "    \"slotsNumber\":3,\n" +
                "    \"fitsRed\":false,\n" +
                "    \"tileContent\":[]\n" +
                "    },\n" +
                "    \"normalStorage13\":{\n" +
                "    \"north\":\"NONE\",\n" +
                "    \"west\":\"SINGLE\",\n" +
                "    \"south\":\"NONE\",\n" +
                "    \"east\":\"NONE\",\n" +
                "    \"slotsNumber\":3,\n" +
                "    \"fitsRed\":false,\n" +
                "    \"tileContent\":[]\n" +
                "    },\n" +
                "    \"normalStorage14\":{\n" +
                "    \"north\":\"DOUBLE\",\n" +
                "    \"west\":\"NONE\",\n" +
                "    \"south\":\"DOUBLE\",\n" +
                "    \"east\":\"NONE\",\n" +
                "    \"slotsNumber\":3,\n" +
                "    \"fitsRed\":false,\n" +
                "    \"tileContent\":[]\n" +
                "    },\n" +
                "    \"normalStorage15\":{\n" +
                "    \"north\":\"SINGLE\",\n" +
                "    \"west\":\"SINGLE\",\n" +
                "    \"south\":\"DOUBLE\",\n" +
                "    \"east\":\"NONE\",\n" +
                "    \"slotsNumber\":3,\n" +
                "    \"fitsRed\":false,\n" +
                "    \"tileContent\":[]\n" +
                "    },\n" +
                "    \"redStorage1\":{\n" +
                "    \"north\":\"NONE\",\n" +
                "    \"west\":\"UNIVERSAL\",\n" +
                "    \"south\":\"SINGLE\",\n" +
                "    \"east\":\"DOUBLE\",\n" +
                "    \"slotsNumber\":1,\n" +
                "    \"fitsRed\":true,\n" +
                "    \"tileContent\":[]\n" +
                "    },\n" +
                "    \"redStorage2\":{\n" +
                "    \"north\":\"UNIVERSAL\",\n" +
                "    \"west\":\"UNIVERSAL\",\n" +
                "    \"south\":\"NONE\",\n" +
                "    \"east\":\"NONE\",\n" +
                "    \"slotsNumber\":1,\n" +
                "    \"fitsRed\":true,\n" +
                "    \"tileContent\":[]\n" +
                "    },\n" +
                "    \"redStorage3\":{\n" +
                "    \"north\":\"DOUBLE\",\n" +
                "    \"west\":\"UNIVERSAL\",\n" +
                "    \"south\":\"DOUBLE\",\n" +
                "    \"east\":\"DOUBLE\",\n" +
                "    \"slotsNumber\":1,\n" +
                "    \"fitsRed\":true,\n" +
                "    \"tileContent\":[]\n" +
                "    },\n" +
                "    \"redStorage4\":{\n" +
                "    \"north\":\"NONE\",\n" +
                "    \"west\":\"UNIVERSAL\",\n" +
                "    \"south\":\"NONE\",\n" +
                "    \"east\":\"UNIVERSAL\",\n" +
                "    \"slotsNumber\":1,\n" +
                "    \"fitsRed\":true,\n" +
                "    \"tileContent\":[]\n" +
                "    },\n" +
                "    \"redStorage5\":{\n" +
                "    \"north\":\"DOUBLE\",\n" +
                "    \"west\":\"UNIVERSAL\",\n" +
                "    \"south\":\"NONE\",\n" +
                "    \"east\":\"SINGLE\",\n" +
                "    \"slotsNumber\":1,\n" +
                "    \"fitsRed\":true,\n" +
                "    \"tileContent\":[]\n" +
                "    },\n" +
                "    \"redStorage6\":{\n" +
                "    \"north\":\"SINGLE\",\n" +
                "    \"west\":\"UNIVERSAL\",\n" +
                "    \"south\":\"SINGLE\",\n" +
                "    \"east\":\"SINGLE\",\n" +
                "    \"slotsNumber\":1,\n" +
                "    \"fitsRed\":true,\n" +
                "    \"tileContent\":[]\n" +
                "    },\n" +
                "    \"redStorage7\":{\n" +
                "    \"north\":\"NONE\",\n" +
                "    \"west\":\"SINGLE\",\n" +
                "    \"south\":\"NONE\",\n" +
                "    \"east\":\"DOUBLE\",\n" +
                "    \"slotsNumber\":2,\n" +
                "    \"fitsRed\":true,\n" +
                "    \"tileContent\":[]\n" +
                "    },\n" +
                "    \"redStorage8\":{\n" +
                "    \"north\":\"NONE\",\n" +
                "    \"west\":\"SINGLE\",\n" +
                "    \"south\":\"NONE\",\n" +
                "    \"east\":\"NONE\",\n" +
                "    \"slotsNumber\":2,\n" +
                "    \"fitsRed\":true,\n" +
                "    \"tileContent\":[]\n" +
                "    },\n" +
                "    \"redStorage9\":{\n" +
                "    \"north\":\"NONE\",\n" +
                "    \"west\":\"DOUBLE\",\n" +
                "    \"south\":\"NONE\",\n" +
                "    \"east\":\"NONE\",\n" +
                "    \"slotsNumber\":2,\n" +
                "    \"fitsRed\":true,\n" +
                "    \"tileContent\":[]\n" +
                "    }}";
        cannon ="{\"singleCannon1\":{\n" +
                "    \"north\":\"CANNON_SINGLE\",\n" +
                "    \"west\":\"NONE\",\n" +
                "    \"south\":\"SINGLE\",\n" +
                "    \"east\":\"NONE\",\n" +
                "    \"doublePower\":false,\n" +
                "    \"activeState\":true\n" +
                "    },\n" +
                "    \"singleCannon2\":{\n" +
                "    \"north\":\"CANNON_SINGLE\",\n" +
                "    \"west\":\"NONE\",\n" +
                "    \"south\":\"SINGLE\",\n" +
                "    \"east\":\"DOUBLE\",\n" +
                "    \"doublePower\":false,\n" +
                "    \"activeState\":true\n" +
                "    },\n" +
                "    \"singleCannon3\":{\n" +
                "    \"north\":\"CANNON_SINGLE\",\n" +
                "    \"west\":\"NONE\",\n" +
                "    \"south\":\"DOUBLE\",\n" +
                "    \"east\":\"NONE\",\n" +
                "    \"doublePower\":false,\n" +
                "    \"activeState\":true\n" +
                "    },\n" +
                "    \"singleCannon4\":{\n" +
                "    \"north\":\"CANNON_SINGLE\",\n" +
                "    \"west\":\"NONE\",\n" +
                "    \"south\":\"NONE\",\n" +
                "    \"east\":\"DOUBLE\",\n" +
                "    \"doublePower\":false,\n" +
                "    \"activeState\":true\n" +
                "    },\n" +
                "    \"singleCannon5\":{\n" +
                "    \"north\":\"CANNON_SINGLE\",\n" +
                "    \"west\":\"NONE\",\n" +
                "    \"south\":\"NONE\",\n" +
                "    \"east\":\"SINGLE\",\n" +
                "    \"doublePower\":false,\n" +
                "    \"activeState\":true\n" +
                "    },\n" +
                "    \"singleCannon6\":{\n" +
                "    \"north\":\"CANNON_SINGLE\",\n" +
                "    \"west\":\"SINGLE\",\n" +
                "    \"south\":\"NONE\",\n" +
                "    \"east\":\"NONE\",\n" +
                "    \"doublePower\":false,\n" +
                "    \"activeState\":true\n" +
                "    },\n" +
                "    \"singleCannon7\":{\n" +
                "    \"north\":\"CANNON_SINGLE\",\n" +
                "    \"west\":\"NONE\",\n" +
                "    \"south\":\"DOUBLE\",\n" +
                "    \"east\":\"SINGLE\",\n" +
                "    \"doublePower\":false,\n" +
                "    \"activeState\":true\n" +
                "    },\n" +
                "    \"singleCannon8\":{\n" +
                "    \"north\":\"CANNON_SINGLE\",\n" +
                "    \"west\":\"DOUBLE\",\n" +
                "    \"south\":\"NONE\",\n" +
                "    \"east\":\"NONE\",\n" +
                "    \"doublePower\":false,\n" +
                "    \"activeState\":true\n" +
                "    },\n" +
                "    \"singleCannon9\":{\n" +
                "    \"north\":\"CANNON_SINGLE\",\n" +
                "    \"west\":\"NONE\",\n" +
                "    \"south\":\"UNIVERSAL\",\n" +
                "    \"east\":\"SINGLE\",\n" +
                "    \"doublePower\":false,\n" +
                "    \"activeState\":true\n" +
                "    },\n" +
                "    \"singleCannon10\":{\n" +
                "    \"north\":\"CANNON_SINGLE\",\n" +
                "    \"west\":\"DOUBLE\",\n" +
                "    \"south\":\"UNIVERSAL\",\n" +
                "    \"east\":\"SINGLE\",\n" +
                "    \"doublePower\":false,\n" +
                "    \"activeState\":true\n" +
                "    },\n" +
                "    \"singleCannon11\":{\n" +
                "    \"north\":\"CANNON_SINGLE\",\n" +
                "    \"west\":\"SINGLE\",\n" +
                "    \"south\":\"DOUBLE\",\n" +
                "    \"east\":\"NONE\",\n" +
                "    \"doublePower\":false,\n" +
                "    \"activeState\":true\n" +
                "    },\n" +
                "    \"singleCannon12\":{\n" +
                "    \"north\":\"CANNON_SINGLE\",\n" +
                "    \"west\":\"DOUBLE\",\n" +
                "    \"south\":\"DOUBLE\",\n" +
                "    \"east\":\"DOUBLE\",\n" +
                "    \"doublePower\":false,\n" +
                "    \"activeState\":true\n" +
                "    },\n" +
                "    \"singleCannon13\":{\n" +
                "    \"north\":\"CANNON_SINGLE\",\n" +
                "    \"west\":\"SINGLE\",\n" +
                "    \"south\":\"NONE\",\n" +
                "    \"east\":\"UNIVERSAL\",\n" +
                "    \"doublePower\":false,\n" +
                "    \"activeState\":true\n" +
                "    },\n" +
                "    \"singleCannon14\":{\n" +
                "    \"north\":\"CANNON_SINGLE\",\n" +
                "    \"west\":\"SINGLE\",\n" +
                "    \"south\":\"SINGLE\",\n" +
                "    \"east\":\"SINGLE\",\n" +
                "    \"doublePower\":false,\n" +
                "    \"activeState\":true\n" +
                "    },\n" +
                "    \"singleCannon15\":{\n" +
                "    \"north\":\"CANNON_SINGLE\",\n" +
                "    \"west\":\"SINGLE\",\n" +
                "    \"south\":\"UNIVERSAL\",\n" +
                "    \"east\":\"DOUBLE\",\n" +
                "    \"doublePower\":false,\n" +
                "    \"activeState\":true\n" +
                "    },\n" +
                "    \"singleCannon16\":{\n" +
                "    \"north\":\"CANNON_SINGLE\",\n" +
                "    \"west\":\"NONE\",\n" +
                "    \"south\":\"UNIVERSAL\",\n" +
                "    \"east\":\"DOUBLE\",\n" +
                "    \"doublePower\":false,\n" +
                "    \"activeState\":true\n" +
                "    },\n" +
                "    \"singleCannon17\":{\n" +
                "    \"north\":\"CANNON_SINGLE\",\n" +
                "    \"west\":\"DOUBLE\",\n" +
                "    \"south\":\"UNIVERSAL\",\n" +
                "    \"east\":\"NONE\",\n" +
                "    \"doublePower\":false,\n" +
                "    \"activeState\":true\n" +
                "    },\n" +
                "    \"singleCannon18\":{\n" +
                "    \"north\":\"CANNON_SINGLE\",\n" +
                "    \"west\":\"SINGLE\",\n" +
                "    \"south\":\"UNIVERSAL\",\n" +
                "    \"east\":\"NONE\",\n" +
                "    \"doublePower\":false,\n" +
                "    \"activeState\":true\n" +
                "    },\n" +
                "    \"singleCannon19\":{\n" +
                "    \"north\":\"CANNON_SINGLE\",\n" +
                "    \"west\":\"NONE\",\n" +
                "    \"south\":\"DOUBLE\",\n" +
                "    \"east\":\"NONE\",\n" +
                "    \"doublePower\":false,\n" +
                "    \"activeState\":true\n" +
                "    },\n" +
                "    \"singleCannon20\":{\n" +
                "    \"north\":\"CANNON_SINGLE\",\n" +
                "    \"west\":\"NONE\",\n" +
                "    \"south\":\"SINGLE\",\n" +
                "    \"east\":\"UNIVERSAL\",\n" +
                "    \"doublePower\":false,\n" +
                "    \"activeState\":true\n" +
                "    },\n" +
                "    \"singleCannon21\":{\n" +
                "    \"north\":\"CANNON_SINGLE\",\n" +
                "    \"west\":\"DOUBLE\",\n" +
                "    \"south\":\"NONE\",\n" +
                "    \"east\":\"SINGLE\",\n" +
                "    \"doublePower\":false,\n" +
                "    \"activeState\":true\n" +
                "    },\n" +
                "    \"singleCannon22\":{\n" +
                "    \"north\":\"CANNON_SINGLE\",\n" +
                "    \"west\":\"UNIVERSAL\",\n" +
                "    \"south\":\"DOUBLE\",\n" +
                "    \"east\":\"NONE\",\n" +
                "    \"doublePower\":false,\n" +
                "    \"activeState\":true\n" +
                "    },\n" +
                "    \"singleCannon23\":{\n" +
                "    \"north\":\"CANNON_SINGLE\",\n" +
                "    \"west\":\"DOUBLE\",\n" +
                "    \"south\":\"SINGLE\",\n" +
                "    \"east\":\"NONE\",\n" +
                "    \"doublePower\":false,\n" +
                "    \"activeState\":true\n" +
                "    },\n" +
                "    \"singleCannon24\":{\n" +
                "    \"north\":\"CANNON_SINGLE\",\n" +
                "    \"west\":\"UNIVERSAL\",\n" +
                "    \"south\":\"NONE\",\n" +
                "    \"east\":\"DOUBLE\",\n" +
                "    \"doublePower\":false,\n" +
                "    \"activeState\":true\n" +
                "    },\n" +
                "    \"singleCannon25\":{\n" +
                "    \"north\":\"CANNON_SINGLE\",\n" +
                "    \"west\":\"NONE\",\n" +
                "    \"south\":\"SINGLE\",\n" +
                "    \"east\":\"NONE\",\n" +
                "    \"doublePower\":false,\n" +
                "    \"activeState\":true\n" +
                "    },\n" +
                "    \"doubleCannon1\":{\n" +
                "    \"north\":\"CANNON_DOUBLE\",\n" +
                "    \"west\":\"NONE\",\n" +
                "    \"south\":\"NONE\",\n" +
                "    \"east\":\"UNIVERSAL\",\n" +
                "    \"doublePower\":true,\n" +
                "    \"activeState\":false\n" +
                "    },\n" +
                "    \"doubleCannon2\":{\n" +
                "    \"north\":\"CANNON_DOUBLE\",\n" +
                "    \"west\":\"NONE\",\n" +
                "    \"south\":\"DOUBLE\",\n" +
                "    \"east\":\"UNIVERSAL\",\n" +
                "    \"doublePower\":true,\n" +
                "    \"activeState\":false\n" +
                "    },\n" +
                "    \"doubleCannon3\":{\n" +
                "    \"north\":\"CANNON_DOUBLE\",\n" +
                "    \"west\":\"UNIVERSAL\",\n" +
                "    \"south\":\"SINGLE\",\n" +
                "    \"east\":\"NONE\",\n" +
                "    \"doublePower\":true,\n" +
                "    \"activeState\":false\n" +
                "    },\n" +
                "    \"doubleCannon4\":{\n" +
                "    \"north\":\"CANNON_DOUBLE\",\n" +
                "    \"west\":\"DOUBLE\",\n" +
                "    \"south\":\"UNIVERSAL\",\n" +
                "    \"east\":\"NONE\",\n" +
                "    \"doublePower\":true,\n" +
                "    \"activeState\":false\n" +
                "    },\n" +
                "    \"doubleCannon5\":{\n" +
                "    \"north\":\"CANNON_DOUBLE\",\n" +
                "    \"west\":\"UNIVERSAL\",\n" +
                "    \"south\":\"NONE\",\n" +
                "    \"east\":\"NONE\",\n" +
                "    \"doublePower\":true,\n" +
                "    \"activeState\":false\n" +
                "    },\n" +
                "    \"doubleCannon6\":{\n" +
                "    \"north\":\"CANNON_DOUBLE\",\n" +
                "    \"west\":\"DOUBLE\",\n" +
                "    \"south\":\"SINGLE\",\n" +
                "    \"east\":\"DOUBLE\",\n" +
                "    \"doublePower\":true,\n" +
                "    \"activeState\":false\n" +
                "    },\n" +
                "    \"doubleCannon7\":{\n" +
                "    \"north\":\"CANNON_DOUBLE\",\n" +
                "    \"west\":\"SINGLE\",\n" +
                "    \"south\":\"NONE\",\n" +
                "    \"east\":\"DOUBLE\",\n" +
                "    \"doublePower\":true,\n" +
                "    \"activeState\":false\n" +
                "    },\n" +
                "    \"doubleCannon8\":{\n" +
                "    \"north\":\"CANNON_DOUBLE\",\n" +
                "    \"west\":\"SINGLE\",\n" +
                "    \"south\":\"DOUBLE\",\n" +
                "    \"east\":\"SINGLE\",\n" +
                "    \"doublePower\":true,\n" +
                "    \"activeState\":false\n" +
                "    },\n" +
                "    \"doubleCannon9\":{\n" +
                "    \"north\":\"CANNON_DOUBLE\",\n" +
                "    \"west\":\"NONE\",\n" +
                "    \"south\":\"SINGLE\",\n" +
                "    \"east\":\"NONE\",\n" +
                "    \"doublePower\":true,\n" +
                "    \"activeState\":false\n" +
                "    },\n" +
                "    \"doubleCannon10\":{\n" +
                "    \"north\":\"CANNON_DOUBLE\",\n" +
                "    \"west\":\"NONE\",\n" +
                "    \"south\":\"UNIVERSAL\",\n" +
                "    \"east\":\"SINGLE\",\n" +
                "    \"doublePower\":true,\n" +
                "    \"activeState\":false\n" +
                "    },\n" +
                "    \"doubleCannon11\":{\n" +
                "    \"north\":\"CANNON_DOUBLE\",\n" +
                "    \"west\":\"NONE\",\n" +
                "    \"south\":\"DOUBLE\",\n" +
                "    \"east\":\"NONE\",\n" +
                "    \"doublePower\":true,\n" +
                "    \"activeState\":false\n" +
                "    }}";
        shield ="{\"shield1\":{\n" +
                "    \"north\":\"DOUBLE\",\n" +
                "    \"west\":\"SINGLE\",\n" +
                "    \"south\":\"DOUBLE\",\n" +
                "    \"east\":\"SINGLE\",\n" +
                "    \"orientation\":\"NORTHEAST\",\n" +
                "    \"activeState\":false\n" +
                "    },\n" +
                "    \"shield2\":{\n" +
                "    \"north\":\"SINGLE\",\n" +
                "    \"west\":\"SINGLE\",\n" +
                "    \"south\":\"SINGLE\",\n" +
                "    \"east\":\"NONE\",\n" +
                "    \"orientation\":\"NORTHEAST\",\n" +
                "    \"activeState\":false\n" +
                "    },\n" +
                "    \"shield3\":{\n" +
                "    \"north\":\"NONE\",\n" +
                "    \"west\":\"SINGLE\",\n" +
                "    \"south\":\"UNIVERSAL\",\n" +
                "    \"east\":\"SINGLE\",\n" +
                "    \"orientation\":\"NORTHEAST\",\n" +
                "    \"activeState\":false\n" +
                "    },\n" +
                "    \"shield4\":{\n" +
                "    \"north\":\"NONE\",\n" +
                "    \"west\":\"DOUBLE\",\n" +
                "    \"south\":\"DOUBLE\",\n" +
                "    \"east\":\"DOUBLE\",\n" +
                "    \"orientation\":\"NORTHEAST\",\n" +
                "    \"activeState\":false\n" +
                "    },\n" +
                "    \"shield5\":{\n" +
                "    \"north\":\"NONE\",\n" +
                "    \"west\":\"UNIVERSAL\",\n" +
                "    \"south\":\"DOUBLE\",\n" +
                "    \"east\":\"DOUBLE\",\n" +
                "    \"orientation\":\"NORTHEAST\",\n" +
                "    \"activeState\":false\n" +
                "    },\n" +
                "    \"shield6\":{\n" +
                "    \"north\":\"SINGLE\",\n" +
                "    \"west\":\"DOUBLE\",\n" +
                "    \"south\":\"SINGLE\",\n" +
                "    \"east\":\"DOUBLE\",\n" +
                "    \"orientation\":\"NORTHEAST\",\n" +
                "    \"activeState\":false\n" +
                "    },\n" +
                "    \"shield7\":{\n" +
                "    \"north\":\"NONE\",\n" +
                "    \"west\":\"UNIVERSAL\",\n" +
                "    \"south\":\"SINGLE\",\n" +
                "    \"east\":\"NONE\",\n" +
                "    \"orientation\":\"NORTHEAST\",\n" +
                "    \"activeState\":false\n" +
                "    },\n" +
                "    \"shield8\":{\n" +
                "    \"north\":\"NONE\",\n" +
                "    \"west\":\"DOUBLE\",\n" +
                "    \"south\":\"UNIVERSAL\",\n" +
                "    \"east\":\"NONE\",\n" +
                "    \"orientation\":\"NORTHEAST\",\n" +
                "    \"activeState\":false\n" +
                "    }}";
        cabin ="{\"centralCabin1\":{\n" +
                "    \"north\":\"UNIVERSAL\",\n" +
                "    \"west\":\"UNIVERSAL\",\n" +
                "    \"south\":\"UNIVERSAL\",\n" +
                "    \"east\":\"UNIVERSAL\",\n" +
                "    \"inhabitants\":\"NONE\",\n" +
                "    \"mainCapsule\":true,\n" +
                "    \"color\":\"RED\",\n" +
                "    \"pinkAdaptors\":0,\n" +
                "    \"orangeAdaptors\":0\n" +
                "    },\n" +
                "    \"centralCabin2\":{\n" +
                "    \"north\":\"UNIVERSAL\",\n" +
                "    \"west\":\"UNIVERSAL\",\n" +
                "    \"south\":\"UNIVERSAL\",\n" +
                "    \"east\":\"UNIVERSAL\",\n" +
                "    \"inhabitants\":\"NONE\",\n" +
                "    \"mainCapsule\":true,\n" +
                "    \"color\":\"YELLOW\",\n" +
                "    \"pinkAdaptors\":0,\n" +
                "    \"orangeAdaptors\":0\n" +
                "    },\n" +
                "    \"centralCabin3\":{\n" +
                "    \"north\":\"UNIVERSAL\",\n" +
                "    \"west\":\"UNIVERSAL\",\n" +
                "    \"south\":\"UNIVERSAL\",\n" +
                "    \"east\":\"UNIVERSAL\",\n" +
                "    \"inhabitants\":\"NONE\",\n" +
                "    \"mainCapsule\":true,\n" +
                "    \"color\":\"GREEN\",\n" +
                "    \"pinkAdaptors\":0,\n" +
                "    \"orangeAdaptors\":0\n" +
                "    },\n" +
                "    \"centralCabin4\":{\n" +
                "    \"north\":\"UNIVERSAL\",\n" +
                "    \"west\":\"UNIVERSAL\",\n" +
                "    \"south\":\"UNIVERSAL\",\n" +
                "    \"east\":\"UNIVERSAL\",\n" +
                "    \"inhabitants\":\"NONE\",\n" +
                "    \"mainCapsule\":true,\n" +
                "    \"color\":\"BLUE\",\n" +
                "    \"pinkAdaptors\":0,\n" +
                "    \"orangeAdaptors\":0\n" +
                "    },\n" +
                "    \"cabin1\":{\n" +
                "    \"north\":\"DOUBLE\",\n" +
                "    \"west\":\"SINGLE\",\n" +
                "    \"south\":\"NONE\",\n" +
                "    \"east\":\"NONE\",\n" +
                "    \"inhabitants\":\"NONE\",\n" +
                "    \"mainCapsule\":false,\n" +
                "    \"color\":\"NONE\",\n" +
                "    \"pinkAdaptors\":0,\n" +
                "    \"orangeAdaptors\":0\n" +
                "    },\n" +
                "    \"cabin2\":{\n" +
                "    \"north\":\"SINGLE\",\n" +
                "    \"west\":\"SINGLE\",\n" +
                "    \"south\":\"DOUBLE\",\n" +
                "    \"east\":\"DOUBLE\",\n" +
                "    \"inhabitants\":\"NONE\",\n" +
                "    \"mainCapsule\":false,\n" +
                "    \"color\":\"NONE\",\n" +
                "    \"pinkAdaptors\":0,\n" +
                "    \"orangeAdaptors\":0\n" +
                "    },\n" +
                "    \"cabin3\":{\n" +
                "    \"north\":\"NONE\",\n" +
                "    \"west\":\"UNIVERSAL\",\n" +
                "    \"south\":\"SINGLE\",\n" +
                "    \"east\":\"SINGLE\",\n" +
                "    \"inhabitants\":\"NONE\",\n" +
                "    \"mainCapsule\":false,\n" +
                "    \"color\":\"NONE\",\n" +
                "    \"pinkAdaptors\":0,\n" +
                "    \"orangeAdaptors\":0\n" +
                "    },\n" +
                "    \"cabin4\":{\n" +
                "    \"north\":\"DOUBLE\",\n" +
                "    \"west\":\"SINGLE\",\n" +
                "    \"south\":\"NONE\",\n" +
                "    \"east\":\"SINGLE\",\n" +
                "    \"inhabitants\":\"NONE\",\n" +
                "    \"mainCapsule\":false,\n" +
                "    \"color\":\"NONE\",\n" +
                "    \"pinkAdaptors\":0,\n" +
                "    \"orangeAdaptors\":0\n" +
                "    },\n" +
                "    \"cabin5\":{\n" +
                "    \"north\":\"NONE\",\n" +
                "    \"west\":\"DOUBLE\",\n" +
                "    \"south\":\"UNIVERSAL\",\n" +
                "    \"east\":\"SINGLE\",\n" +
                "    \"inhabitants\":\"NONE\",\n" +
                "    \"mainCapsule\":false,\n" +
                "    \"color\":\"NONE\",\n" +
                "    \"pinkAdaptors\":0,\n" +
                "    \"orangeAdaptors\":0\n" +
                "    },\n" +
                "    \"cabin6\":{\n" +
                "    \"north\":\"DOUBLE\",\n" +
                "    \"west\":\"SINGLE\",\n" +
                "    \"south\":\"DOUBLE\",\n" +
                "    \"east\":\"SINGLE\",\n" +
                "    \"inhabitants\":\"NONE\",\n" +
                "    \"mainCapsule\":false,\n" +
                "    \"color\":\"NONE\",\n" +
                "    \"pinkAdaptors\":0,\n" +
                "    \"orangeAdaptors\":0\n" +
                "    },\n" +
                "    \"cabin7\":{\n" +
                "    \"north\":\"DOUBLE\",\n" +
                "    \"west\":\"UNIVERSAL\",\n" +
                "    \"south\":\"NONE\",\n" +
                "    \"east\":\"DOUBLE\",\n" +
                "    \"inhabitants\":\"NONE\",\n" +
                "    \"mainCapsule\":false,\n" +
                "    \"color\":\"NONE\",\n" +
                "    \"pinkAdaptors\":0,\n" +
                "    \"orangeAdaptors\":0\n" +
                "    },\n" +
                "    \"cabin8\":{\n" +
                "    \"north\":\"DOUBLE\",\n" +
                "    \"west\":\"DOUBLE\",\n" +
                "    \"south\":\"DOUBLE\",\n" +
                "    \"east\":\"SINGLE\",\n" +
                "    \"inhabitants\":\"NONE\",\n" +
                "    \"mainCapsule\":false,\n" +
                "    \"color\":\"NONE\",\n" +
                "    \"pinkAdaptors\":0,\n" +
                "    \"orangeAdaptors\":0\n" +
                "    },\n" +
                "    \"cabin9\":{\n" +
                "    \"north\":\"SINGLE\",\n" +
                "    \"west\":\"UNIVERSAL\",\n" +
                "    \"south\":\"DOUBLE\",\n" +
                "    \"east\":\"NONE\",\n" +
                "    \"inhabitants\":\"NONE\",\n" +
                "    \"mainCapsule\":false,\n" +
                "    \"color\":\"NONE\",\n" +
                "    \"pinkAdaptors\":0,\n" +
                "    \"orangeAdaptors\":0\n" +
                "    },\n" +
                "    \"cabin10\":{\n" +
                "    \"north\":\"UNIVERSAL\",\n" +
                "    \"west\":\"DOUBLE\",\n" +
                "    \"south\":\"NONE\",\n" +
                "    \"east\":\"NONE\",\n" +
                "    \"inhabitants\":\"NONE\",\n" +
                "    \"mainCapsule\":false,\n" +
                "    \"color\":\"NONE\",\n" +
                "    \"pinkAdaptors\":0,\n" +
                "    \"orangeAdaptors\":0\n" +
                "    },\n" +
                "    \"cabin11\":{\n" +
                "    \"north\":\"NONE\",\n" +
                "    \"west\":\"UNIVERSAL\",\n" +
                "    \"south\":\"NONE\",\n" +
                "    \"east\":\"DOUBLE\",\n" +
                "    \"inhabitants\":\"NONE\",\n" +
                "    \"mainCapsule\":false,\n" +
                "    \"color\":\"NONE\",\n" +
                "    \"pinkAdaptors\":0,\n" +
                "    \"orangeAdaptors\":0\n" +
                "    },\n" +
                "    \"cabin12\":{\n" +
                "    \"north\":\"DOUBLE\",\n" +
                "    \"west\":\"DOUBLE\",\n" +
                "    \"south\":\"NONE\",\n" +
                "    \"east\":\"DOUBLE\",\n" +
                "    \"inhabitants\":\"NONE\",\n" +
                "    \"mainCapsule\":false,\n" +
                "    \"color\":\"NONE\",\n" +
                "    \"pinkAdaptors\":0,\n" +
                "    \"orangeAdaptors\":0\n" +
                "    },\n" +
                "    \"cabin13\":{\n" +
                "    \"north\":\"DOUBLE\",\n" +
                "    \"west\":\"UNIVERSAL\",\n" +
                "    \"south\":\"DOUBLE\",\n" +
                "    \"east\":\"NONE\",\n" +
                "    \"inhabitants\":\"NONE\",\n" +
                "    \"mainCapsule\":false,\n" +
                "    \"color\":\"NONE\",\n" +
                "    \"pinkAdaptors\":0,\n" +
                "    \"orangeAdaptors\":0\n" +
                "    },\n" +
                "    \"cabin14\":{\n" +
                "    \"north\":\"NONE\",\n" +
                "    \"west\":\"UNIVERSAL\",\n" +
                "    \"south\":\"NONE\",\n" +
                "    \"east\":\"SINGLE\",\n" +
                "    \"inhabitants\":\"NONE\",\n" +
                "    \"mainCapsule\":false,\n" +
                "    \"color\":\"NONE\",\n" +
                "    \"pinkAdaptors\":0,\n" +
                "    \"orangeAdaptors\":0\n" +
                "    },\n" +
                "    \"cabin15\":{\n" +
                "    \"north\":\"SINGLE\",\n" +
                "    \"west\":\"SINGLE\",\n" +
                "    \"south\":\"SINGLE\",\n" +
                "    \"east\":\"DOUBLE\",\n" +
                "    \"inhabitants\":\"NONE\",\n" +
                "    \"mainCapsule\":false,\n" +
                "    \"color\":\"NONE\",\n" +
                "    \"pinkAdaptors\":0,\n" +
                "    \"orangeAdaptors\":0\n" +
                "    },\n" +
                "    \"cabin16\":{\n" +
                "    \"north\":\"NONE\",\n" +
                "    \"west\":\"SINGLE\",\n" +
                "    \"south\":\"UNIVERSAL\",\n" +
                "    \"east\":\"NONE\",\n" +
                "    \"inhabitants\":\"NONE\",\n" +
                "    \"mainCapsule\":false,\n" +
                "    \"color\":\"NONE\",\n" +
                "    \"pinkAdaptors\":0,\n" +
                "    \"orangeAdaptors\":0\n" +
                "    },\n" +
                "    \"cabin17\":{\n" +
                "    \"north\":\"SINGLE\",\n" +
                "    \"west\":\"UNIVERSAL\",\n" +
                "    \"south\":\"SINGLE\",\n" +
                "    \"east\":\"NONE\",\n" +
                "    \"inhabitants\":\"NONE\",\n" +
                "    \"mainCapsule\":false,\n" +
                "    \"color\":\"NONE\",\n" +
                "    \"pinkAdaptors\":0,\n" +
                "    \"orangeAdaptors\":0\n" +
                "    }}";
        battery ="{\"battery1\":{\n" +
                "    \"north\":\"UNIVERSAL\",\n" +
                "    \"west\":\"NONE\",\n" +
                "    \"south\":\"SINGLE\",\n" +
                "    \"east\":\"DOUBLE\",\n" +
                "    \"slotsNumber\":2,\n" +
                "    \"slotsFilled\":2\n" +
                "    },\n" +
                "    \"battery2\":{\n" +
                "    \"north\":\"NONE\",\n" +
                "    \"west\":\"UNIVERSAL\",\n" +
                "    \"south\":\"NONE\",\n" +
                "    \"east\":\"NONE\",\n" +
                "    \"slotsNumber\":2,\n" +
                "    \"slotsFilled\":2\n" +
                "    },\n" +
                "    \"battery3\":{\n" +
                "    \"north\":\"UNIVERSAL\",\n" +
                "    \"west\":\"SINGLE\",\n" +
                "    \"south\":\"SINGLE\",\n" +
                "    \"east\":\"SINGLE\",\n" +
                "    \"slotsNumber\":2,\n" +
                "    \"slotsFilled\":2\n" +
                "    },\n" +
                "    \"battery4\":{\n" +
                "    \"north\":\"UNIVERSAL\",\n" +
                "    \"west\":\"UNIVERSAL\",\n" +
                "    \"south\":\"NONE\",\n" +
                "    \"east\":\"NONE\",\n" +
                "    \"slotsNumber\":2,\n" +
                "    \"slotsFilled\":2\n" +
                "    },\n" +
                "    \"battery5\":{\n" +
                "    \"north\":\"UNIVERSAL\",\n" +
                "    \"west\":\"NONE\",\n" +
                "    \"south\":\"DOUBLE\",\n" +
                "    \"east\":\"SINGLE\",\n" +
                "    \"slotsNumber\":2,\n" +
                "    \"slotsFilled\":2\n" +
                "    },\n" +
                "    \"battery6\":{\n" +
                "    \"north\":\"UNIVERSAL\",\n" +
                "    \"west\":\"NONE\",\n" +
                "    \"south\":\"NONE\",\n" +
                "    \"east\":\"DOUBLE\",\n" +
                "    \"slotsNumber\":2,\n" +
                "    \"slotsFilled\":2\n" +
                "    },\n" +
                "    \"battery7\":{\n" +
                "    \"north\":\"DOUBLE\",\n" +
                "    \"west\":\"SINGLE\",\n" +
                "    \"south\":\"DOUBLE\",\n" +
                "    \"east\":\"SINGLE\",\n" +
                "    \"slotsNumber\":2,\n" +
                "    \"slotsFilled\":2\n" +
                "    },\n" +
                "    \"battery8\":{\n" +
                "    \"north\":\"NONE\",\n" +
                "    \"west\":\"UNIVERSAL\",\n" +
                "    \"south\":\"NONE\",\n" +
                "    \"east\":\"UNIVERSAL\",\n" +
                "    \"slotsNumber\":2,\n" +
                "    \"slotsFilled\":2\n" +
                "    },\n" +
                "    \"battery9\":{\n" +
                "    \"north\":\"NONE\",\n" +
                "    \"west\":\"UNIVERSAL\",\n" +
                "    \"south\":\"NONE\",\n" +
                "    \"east\":\"NONE\",\n" +
                "    \"slotsNumber\":2,\n" +
                "    \"slotsFilled\":2\n" +
                "    },\n" +
                "    \"battery10\":{\n" +
                "    \"north\":\"UNIVERSAL\",\n" +
                "    \"west\":\"DOUBLE\",\n" +
                "    \"south\":\"DOUBLE\",\n" +
                "    \"east\":\"DOUBLE\",\n" +
                "    \"slotsNumber\":2,\n" +
                "    \"slotsFilled\":2\n" +
                "    },\n" +
                "    \"battery11\":{\n" +
                "    \"north\":\"UNIVERSAL\",\n" +
                "    \"west\":\"SINGLE\",\n" +
                "    \"south\":\"NONE\",\n" +
                "    \"east\":\"NONE\",\n" +
                "    \"slotsNumber\":2,\n" +
                "    \"slotsFilled\":2\n" +
                "    },\n" +
                "    \"batteryTriple1\":{\n" +
                "    \"north\":\"SINGLE\",\n" +
                "    \"west\":\"NONE\",\n" +
                "    \"south\":\"DOUBLE\",\n" +
                "    \"east\":\"NONE\",\n" +
                "    \"slotsNumber\":3,\n" +
                "    \"slotsFilled\":3\n" +
                "    },\n" +
                "    \"batteryTriple2\":{\n" +
                "    \"north\":\"SINGLE\",\n" +
                "    \"west\":\"NONE\",\n" +
                "    \"south\":\"DOUBLE\",\n" +
                "    \"east\":\"SINGLE\",\n" +
                "    \"slotsNumber\":3,\n" +
                "    \"slotsFilled\":3\n" +
                "    },\n" +
                "    \"batteryTriple3\":{\n" +
                "    \"north\":\"DOUBLE\",\n" +
                "    \"west\":\"NONE\",\n" +
                "    \"south\":\"NONE\",\n" +
                "    \"east\":\"SINGLE\",\n" +
                "    \"slotsNumber\":3,\n" +
                "    \"slotsFilled\":3\n" +
                "    },\n" +
                "    \"batteryTriple4\":{\n" +
                "    \"north\":\"DOUBLE\",\n" +
                "    \"west\":\"NONE\",\n" +
                "    \"south\":\"NONE\",\n" +
                "    \"east\":\"NONE\",\n" +
                "    \"slotsNumber\":3,\n" +
                "    \"slotsFilled\":3\n" +
                "    },\n" +
                "    \"batteryTriple5\":{\n" +
                "    \"north\":\"NONE\",\n" +
                "    \"west\":\"SINGLE\",\n" +
                "    \"south\":\"NONE\",\n" +
                "    \"east\":\"NONE\",\n" +
                "    \"slotsNumber\":3,\n" +
                "    \"slotsFilled\":3\n" +
                "    },\n" +
                "    \"batteryTriple6\":{\n" +
                "    \"north\":\"DOUBLE\",\n" +
                "    \"west\":\"NONE\",\n" +
                "    \"south\":\"SINGLE\",\n" +
                "    \"east\":\"DOUBLE\",\n" +
                "    \"slotsNumber\":3,\n" +
                "    \"slotsFilled\":3\n" +
                "    }}";
        connector ="{\"connector1\":{\n" +
                "    \"northConnector\":\"DOUBLE\",\n" +
                "    \"westConnector\":\"UNIVERSAL\",\n" +
                "    \"southConnector\":\"NONE\",\n" +
                "    \"eastConnector\":\"UNIVERSAL\"\n" +
                "    },\n" +
                "    \"connector2\":{\n" +
                "    \"northConnector\":\"SINGLE\",\n" +
                "    \"westConnector\":\"UNIVERSAL\",\n" +
                "    \"southConnector\":\"SINGLE\",\n" +
                "    \"eastConnector\":\"UNIVERSAL\"\n" +
                "    },\n" +
                "    \"connector3\":{\n" +
                "    \"northConnector\":\"UNIVERSAL\",\n" +
                "    \"westConnector\":\"UNIVERSAL\",\n" +
                "    \"southConnector\":\"DOUBLE\",\n" +
                "    \"eastConnector\":\"DOUBLE\"\n" +
                "    },\n" +
                "    \"connector4\":{\n" +
                "    \"northConnector\":\"SINGLE\",\n" +
                "    \"westConnector\":\"UNIVERSAL\",\n" +
                "    \"southConnector\":\"NONE\",\n" +
                "    \"eastConnector\":\"UNIVERSAL\"\n" +
                "    },\n" +
                "    \"connector5\":{\n" +
                "    \"northConnector\":\"UNIVERSAL\",\n" +
                "    \"westConnector\":\"SINGLE\",\n" +
                "    \"southConnector\":\"NONE\",\n" +
                "    \"eastConnector\":\"UNIVERSAL\"\n" +
                "    },\n" +
                "    \"connector6\":{\n" +
                "    \"northConnector\":\"UNIVERSAL\",\n" +
                "    \"westConnector\":\"UNIVERSAL\",\n" +
                "    \"southConnector\":\"DOUBLE\",\n" +
                "    \"eastConnector\":\"SINGLE\"\n" +
                "    },\n" +
                "    \"connector7\":{\n" +
                "    \"northConnector\":\"SINGLE\",\n" +
                "    \"westConnector\":\"UNIVERSAL\",\n" +
                "    \"southConnector\":\"DOUBLE\",\n" +
                "    \"eastConnector\":\"UNIVERSAL\"\n" +
                "    },\n" +
                "    \"connector8\":{\n" +
                "    \"northConnector\":\"UNIVERSAL\",\n" +
                "    \"westConnector\":\"UNIVERSAL\",\n" +
                "    \"southConnector\":\"NONE\",\n" +
                "    \"eastConnector\":\"DOUBLE\"\n" +
                "    }}";
        bioadaptor="{\"orangeBioadaptor1\":{\n" +
                "    \"north\":\"NONE\",\n" +
                "    \"west\":\"UNIVERSAL\",\n" +
                "    \"south\":\"NONE\",\n" +
                "    \"east\":\"DOUBLE\",\n" +
                "    \"color\":\"ORANGE\"\n" +
                "    },\n" +
                "    \"orangeBioadaptor2\":{\n" +
                "    \"north\":\"DOUBLE\",\n" +
                "    \"west\":\"SINGLE\",\n" +
                "    \"south\":\"NONE\",\n" +
                "    \"east\":\"SINGLE\",\n" +
                "    \"color\":\"ORANGE\"\n" +
                "    },\n" +
                "    \"orangeBioadaptor3\":{\n" +
                "    \"north\":\"NONE\",\n" +
                "    \"west\":\"UNIVERSAL\",\n" +
                "    \"south\":\"SINGLE\",\n" +
                "    \"east\":\"NONE\",\n" +
                "    \"color\":\"ORANGE\"\n" +
                "    },\n" +
                "    \"orangeBioadaptor4\":{\n" +
                "    \"north\":\"SINGLE\",\n" +
                "    \"west\":\"SINGLE\",\n" +
                "    \"south\":\"NONE\",\n" +
                "    \"east\":\"SINGLE\",\n" +
                "    \"color\":\"ORANGE\"\n" +
                "    },\n" +
                "    \"orangeBioadaptor5\":{\n" +
                "    \"north\":\"NONE\",\n" +
                "    \"west\":\"UNIVERSAL\",\n" +
                "    \"south\":\"NONE\",\n" +
                "    \"east\":\"NONE\",\n" +
                "    \"color\":\"ORANGE\"\n" +
                "    },\n" +
                "    \"orangeBioadaptor6\":{\n" +
                "    \"north\":\"SINGLE\",\n" +
                "    \"west\":\"UNIVERSAL\",\n" +
                "    \"south\":\"NONE\",\n" +
                "    \"east\":\"NONE\",\n" +
                "    \"color\":\"ORANGE\"\n" +
                "    },\n" +
                "    \"purpleBioadaptor1\":{\n" +
                "    \"north\":\"NONE\",\n" +
                "    \"west\":\"UNIVERSAL\",\n" +
                "    \"south\":\"NONE\",\n" +
                "    \"east\":\"SINGLE\",\n" +
                "    \"color\":\"PURPLE\"\n" +
                "    },\n" +
                "    \"purpleBioadaptor2\":{\n" +
                "    \"north\":\"DOUBLE\",\n" +
                "    \"west\":\"DOUBLE\",\n" +
                "    \"south\":\"NONE\",\n" +
                "    \"east\":\"DOUBLE\",\n" +
                "    \"color\":\"PURPLE\"\n" +
                "    },\n" +
                "    \"purpleBioadaptor3\":{\n" +
                "    \"north\":\"DOUBLE\",\n" +
                "    \"west\":\"UNIVERSAL\",\n" +
                "    \"south\":\"NONE\",\n" +
                "    \"east\":\"NONE\",\n" +
                "    \"color\":\"PURPLE\"\n" +
                "    },\n" +
                "    \"purpleBioadaptor4\":{\n" +
                "    \"north\":\"SINGLE\",\n" +
                "    \"west\":\"DOUBLE\",\n" +
                "    \"south\":\"NONE\",\n" +
                "    \"east\":\"DOUBLE\",\n" +
                "    \"color\":\"PURPLE\"\n" +
                "    },\n" +
                "    \"purpleBioadaptor5\":{\n" +
                "    \"north\":\"NONE\",\n" +
                "    \"west\":\"UNIVERSAL\",\n" +
                "    \"south\":\"NONE\",\n" +
                "    \"east\":\"NONE\",\n" +
                "    \"color\":\"PURPLE\"\n" +
                "    },\n" +
                "    \"purpleBioadaptor6\":{\n" +
                "    \"north\":\"NONE\",\n" +
                "    \"west\":\"UNIVERSAL\",\n" +
                "    \"south\":\"DOUBLE\",\n" +
                "    \"east\":\"NONE\",\n" +
                "    \"color\":\"PURPLE\"\n" +
                "    }}";
    }
}

/**
 * CannonTileParse is used to translate the parameters from Json to actual CannonTile class
 */
class CannonTileParse{
    public boolean doublePower;
    public boolean activeState;
    public ConnectorType north;
    public ConnectorType west;
    public ConnectorType south;  // Qui usa il tipo enum
    public ConnectorType east;
}

/**
 * ShieldTileParse is used to translate the parameters from Json to actual ShieldTile class
 */
class ShieldTileParse{
    public ShieldOrientation orientation;
    public boolean activeState;
    public ConnectorType north;
    public ConnectorType west;
    public ConnectorType south;  // Qui usa il tipo enum
    public ConnectorType east;
}

/**
 *  ShieldTileParse is used to translate the parameters from Json to actual ShieldTile class
 */
class CabinTileParse{
    public ConnectorType north;
    public ConnectorType west;
    public ConnectorType south;  // Qui usa il tipo enum
    public ConnectorType east;
    public CabinInhabitants inhabitants;
    public boolean mainCapsule;
    public Color color;
    public int pinkAdaptors;
    public int orangeAdaptors;
}

/**
 * EngineTileParse is used to translate the parameters from Json to actual EngineTile class
 */
//Da finire perchè c'è Direction
class EngineTileParse{
    public boolean doublePower;
    public boolean activeState;
    public ConnectorType north;
    public ConnectorType west;
    public ConnectorType south;  // Qui usa il tipo enum
    public ConnectorType east;
}

/**
 * BioadaptorTileParse is used to translate the parameters from Json to actual BioadaptorTile class
 */
class BioadaptorTileParse{
    public ConnectorType north;
    public ConnectorType west;
    public ConnectorType south;  // Qui usa il tipo enum
    public ConnectorType east;
    public AlienColor color;
}

/**
 *  CargoTileParse is used to translate the parameters from Json to actual CargoTile class
 */
class CargoTileParse{
    public ConnectorType north;
    public ConnectorType west;
    public ConnectorType south;  // Qui usa il tipo enum
    public ConnectorType east;
    public int slotsNumber;
    public boolean fitsRed;
    public List<Integer> tileContent;
}

/**
 * BatteryTilePars is used to translate the parameters from Json to actual BatteryTile class
 */
class BatteryTileParse{
    public int slotsNumber;
    public int slotsFilled;
    public ConnectorType north;
    public ConnectorType west;
    public ConnectorType south;  // Qui usa il tipo enum
    public ConnectorType east;
}

/**
 * TilePars is used to translate the parameters from Json to actual Tile class
 */
class TileParse {
    public ConnectorType northConnector;
    public ConnectorType westConnector;
    public ConnectorType southConnector;  // Qui usa il tipo enum
    public ConnectorType eastConnector;
}

/**
 * JsonParsing is used to build different Tiles from Json(JsonCards)
 */
public class JsonParsing{
    private ArrayList<Tile> connectors;
    private ArrayList<CabinTile> cabins;
    private ArrayList<CabinTile> mainCabins;
    private ArrayList<BatteryTile> batteries;
    private ArrayList<EngineTile> engines;
    private ArrayList<CargoTile> cargos;
    private ArrayList<CannonTile> cannons;
    private ArrayList<ShieldTile> shields;
    private ArrayList<BioadaptorTile> bioadaptors;

    /**
     * Returns a complete List of Tiles present in Game
     * @return List<Tile>
     */
    public List<Tile> getCompleteList(){ // NB main capsules not included in tilePile obv
        List<Tile> completeList = new ArrayList<>();
        completeList.addAll(connectors);
        for (CabinTile tile : cabins) {
            if (!tile.isMainCapsule()) {
                completeList.add(tile);
            }
        }
        completeList.addAll(batteries);
        completeList.addAll(engines);
        completeList.addAll(cargos);
        completeList.addAll(cannons);
        completeList.addAll(shields);
        completeList.addAll(bioadaptors);

        return completeList;
    }

    /**
     * This function returns a full ArrayList of Tile
     * @return ArrayList<Tile>
     */
    public ArrayList<Tile> getConnectors(){
        return connectors;
    }

    /**
     * This function returns a full ArrayList of CabinTile
     * @return ArrayList<CabinTile>
     */
    public ArrayList<CabinTile> getCabins(){
        return cabins;
    }

    /**
     * This function returns a full ArrayList of main CabinTile
     * @return ArrayList<CabinTile>
     */
    public ArrayList<CabinTile> getMainCabins(){
        return mainCabins;
    }

    /**
     * This function returns a full ArrayList of BatteryTile
     * @return ArrayList<BatteryTile>
     */
    public ArrayList<BatteryTile> getBatteries(){
        return batteries;
    }

    /**
     * This function returns a full ArrayList of EngineTile
     * @return ArrayList<EngineTile>
     */
    public ArrayList<EngineTile> getEngines(){
        return engines;
    }

    /**
     * This function returns a full ArrayList of CargoTile
     * @return ArrayList<CargoTile>
     */
    public ArrayList<CargoTile> getCargos(){
        return cargos;
    }

    /**
     * This function returns a full ArrayList of CannonTile
     * @return ArrayList<CannonTile>
     */
    public ArrayList<CannonTile> getCannons(){
        return cannons;
    }

    /**
     * This function returns a full ArrayList of ShieldTile
     * @return ArrayList<ShieldTile>
     */
    public ArrayList<ShieldTile> getShields(){
        return shields;
    }

    /**
     * This function returns a full ArrayList of BioadaptorTile
     * @return ArrayList<BioadaptorTile>
     */
    public ArrayList<BioadaptorTile> getBioadaptors(){
        return bioadaptors;
    }

    /**
     * Sets connectors ArrayList in JsonParsing
     * @param connectors
     */
    public void setConnectors(ArrayList<Tile> connectors){
        this.connectors = connectors;
    }

    /**
     * Sets cabins ArrayList in JsonParsing
     * @param cabins
     */
    public void setCabins(ArrayList<CabinTile> cabins){
        this.cabins = cabins;
    }

    /**
     * Sets mainCabins ArrayList in JsonParsing
     * @param mainCabins
     */
    public void setMainCabins(ArrayList<CabinTile> mainCabins) {
        this.mainCabins = mainCabins;
    }

    /**
     * Sets batteries ArrayList in JsonParsing
     * @param batteries
     */
    public void setBatteries(ArrayList<BatteryTile> batteries){
        this.batteries = batteries;
    }

    /**
     * Sets engines ArrayList in JsonParsing
     * @param engines
     */
    public void setEngines(ArrayList<EngineTile> engines){
        this.engines = engines;
    }

    /**
     * Sets cargos ArrayList in JsonParsing
     * @param cargos
     */
    public void setCargos(ArrayList<CargoTile> cargos){
        this.cargos = cargos;
    }

    /**
     * Sets cannons ArrayList in JsonParsing
     * @param cannons
     */
    public void setCannons(ArrayList<CannonTile> cannons){
        this.cannons = cannons;
    }

    /**
     * Sets shields ArrayList in JsonParsing
     * @param shields
     */
    public void setShields(ArrayList<ShieldTile> shields){
        this.shields = shields;
    }

    /**
     * Sets bioadaptors ArrayList in JsonParsing
     * @param bioadaptors
     */
    public void setBioadaptors(ArrayList<BioadaptorTile> bioadaptors){
        this.bioadaptors = bioadaptors;
    }

    /**
     * Constructor of JsonParsing that converts JsonTiles Attributes into actual Tiles
     */
    public JsonParsing(){
        JsonTiles jsonTiles = new JsonTiles();
        Gson gson = new Gson();

        ArrayList<Tile> connectorTiles = new ArrayList<>();
        Type type = new TypeToken<Map<String,TileParse>>(){}.getType();
        Map<String, TileParse> connectors = gson.fromJson(jsonTiles.connector, type);
        for (String connectorKey : connectors.keySet()) {
            TileParse connectorParse = connectors.get(connectorKey);
            // create an object for any new connector
            Tile connector = new Tile(
                    connectorParse.northConnector,
                    connectorParse.westConnector,
                    connectorParse.southConnector,
                    connectorParse.eastConnector
            );
            connectorTiles.add(connector);
            connector.setName(connectorKey);
        }

        ArrayList<CannonTile> cannonTiles = new ArrayList<>();
        Type typeCannon = new TypeToken<Map<String,CannonTileParse>>(){}.getType();
        Map<String, CannonTileParse> cannonsMap = gson.fromJson(jsonTiles.cannon, typeCannon);
        for (String connectorKey : cannonsMap.keySet()) {
            CannonTileParse cannonParse = cannonsMap.get(connectorKey);
            CannonTile cannon = new CannonTile(cannonParse.north,cannonParse.south,cannonParse.east,cannonParse.west,cannonParse.doublePower,cannonParse.activeState);
            cannonTiles.add(cannon);
            cannon.setName(connectorKey);
        }

        ArrayList<ShieldTile> shieldTiles = new ArrayList<>();
        Type typeShield = new TypeToken<Map<String,ShieldTileParse>>(){}.getType();
        Map<String,ShieldTileParse> shieldsMap = gson.fromJson(jsonTiles.shield, typeShield);
        for (String connectorKey : shieldsMap.keySet()) {
            ShieldTileParse shieldParse = shieldsMap.get(connectorKey);
            ShieldTile shield = new ShieldTile(shieldParse.north,shieldParse.south,shieldParse.east,shieldParse.west,shieldParse.orientation,shieldParse.activeState);
            shieldTiles.add(shield);
            shield.setName(connectorKey);
        }

        ArrayList<CabinTile> cabinTiles = new ArrayList<>();
        ArrayList<CabinTile> mainCabins = new ArrayList<>();
        Type typeCabin = new TypeToken<Map<String,CabinTileParse>>(){}.getType();
        Map<String,CabinTileParse> cabinsMap = gson.fromJson(jsonTiles.cabin, typeCabin);
        for (String connectorKey : cabinsMap.keySet()) {
            CabinTileParse cabinParse = cabinsMap.get(connectorKey);
            CabinTile cabin = new CabinTile(cabinParse.north,cabinParse.south,cabinParse.east,cabinParse.west,cabinParse.inhabitants,cabinParse.mainCapsule,cabinParse.color,cabinParse.pinkAdaptors,cabinParse.orangeAdaptors);
            if (!cabin.isMainCapsule()) {
                cabinTiles.add(cabin);
            } else {
                mainCabins.add(cabin);
            }
            cabin.setName(connectorKey);
        }

        ArrayList<BioadaptorTile> bioadaptorTiles = new ArrayList<>();
        Type typeBioadaptor = new TypeToken<Map<String,BioadaptorTileParse>>(){}.getType();
        Map<String,BioadaptorTileParse> bioadaptorsMap = gson.fromJson(jsonTiles.bioadaptor, typeBioadaptor);
        for (String connectorKey : bioadaptorsMap.keySet()) {
            BioadaptorTileParse bioadaptorParse = bioadaptorsMap.get(connectorKey);
            BioadaptorTile bioadaptor = new BioadaptorTile(bioadaptorParse.north,bioadaptorParse.south,bioadaptorParse.east,bioadaptorParse.west,bioadaptorParse.color);
            bioadaptorTiles.add(bioadaptor);
            bioadaptor.setName(connectorKey);
        }

        ArrayList<CargoTile> cargoTiles = new ArrayList<>();
        Type typeCargo = new TypeToken<Map<String,CargoTileParse>>(){}.getType();
        Map<String,CargoTileParse> cargosMap = gson.fromJson(jsonTiles.cargo, typeCargo);
        for (String connectorKey : cargosMap.keySet()) {
            CargoTileParse cargoParse = cargosMap.get(connectorKey);
            CargoTile cargo = new CargoTile(cargoParse.north,cargoParse.south,cargoParse.east,cargoParse.west,cargoParse.slotsNumber,cargoParse.fitsRed,cargoParse.tileContent);
            cargoTiles.add(cargo);
            cargo.setName(connectorKey);
        }

        ArrayList<BatteryTile> batteryTiles = new ArrayList<>();
        Type typeBattery = new TypeToken<Map<String,BatteryTileParse>>(){}.getType();
        Map<String,BatteryTileParse> batteryMap = gson.fromJson(jsonTiles.battery, typeBattery);
        for (String connectorKey : batteryMap.keySet()) {
            BatteryTileParse batteryParse = batteryMap.get(connectorKey);
            BatteryTile battery = new BatteryTile(batteryParse.north,batteryParse.south,batteryParse.east,batteryParse.west,batteryParse.slotsNumber,batteryParse.slotsFilled);
            batteryTiles.add(battery);
            battery.setName(connectorKey);
        }

        ArrayList<EngineTile> engineTiles = new ArrayList<>();
        Type typeEngine = new TypeToken<Map<String,EngineTileParse>>(){}.getType();
        Map<String,EngineTileParse> engineMap = gson.fromJson(jsonTiles.engine, typeEngine);
        for (String connectorKey : engineMap.keySet()) {
            EngineTileParse engineParse = engineMap.get(connectorKey);
            EngineTile engine = new EngineTile(engineParse.doublePower,engineParse.activeState,engineParse.north,engineParse.south,engineParse.east,engineParse.west);
            engineTiles.add(engine);
            engine.setName(connectorKey);
        }

        setConnectors(connectorTiles);
        setShields(shieldTiles);
        setCabins(cabinTiles);
        setMainCabins(mainCabins);
        setBioadaptors(bioadaptorTiles);
        setCargos(cargoTiles);
        setBatteries(batteryTiles);
        setEngines(engineTiles);
        setCannons(cannonTiles);
        setBioadaptors(bioadaptorTiles);
    }
}
