package org.stellar.sdk;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import org.stellar.base.Keypair;
import org.stellar.sdk.operations.*;

import java.lang.reflect.Type;

class OperationDeserializer implements JsonDeserializer<Operation> {
  @Override
  public Operation deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
    // Create new Gson object with adapters needed in Operation
    Gson gson = new GsonBuilder()
            .registerTypeAdapter(Keypair.class, new KeypairTypeAdapter().nullSafe())
            .create();

    int type = json.getAsJsonObject().get("type_i").getAsInt();
    switch (type) {
      case 0:
        return gson.fromJson(json, CreateAccountOperation.class);
      case 1:
        return gson.fromJson(json, PaymentOperation.class);
      case 2:
        return gson.fromJson(json, PathPaymentOperation.class);
      case 3:
        return gson.fromJson(json, ManageOfferOperation.class);
      case 4:
        return gson.fromJson(json, CreatePassiveOfferOperation.class);
      case 5:
        return gson.fromJson(json, SetOptionsOperation.class);
      case 6:
        return gson.fromJson(json, ChangeTrustOperation.class);
      case 7:
        return gson.fromJson(json, AllowTrustOperation.class);
      case 8:
        return gson.fromJson(json, AccountMergeOperation.class);
      case 9:
        return gson.fromJson(json, InflationOperation.class);
      default:
        throw new RuntimeException("Invalid operation type");
    }
  }
}