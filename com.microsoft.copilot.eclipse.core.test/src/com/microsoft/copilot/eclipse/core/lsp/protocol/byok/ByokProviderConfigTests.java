// Copyright (c) Microsoft Corporation.
// Licensed under the MIT license.

package com.microsoft.copilot.eclipse.core.lsp.protocol.byok;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;

class ByokProviderConfigTests {

  private static final Gson GSON = new Gson();

  @Test
  void testProviderConfig_serializesClsFieldNames() {
    ByokProviderConfig config = new ByokProviderConfig("Ollama", "http://localhost:11434");

    JsonObject json = JsonParser.parseString(GSON.toJson(config)).getAsJsonObject();

    assertEquals("Ollama", json.get("providerName").getAsString());
    assertEquals("http://localhost:11434", json.get("url").getAsString());
  }

  @Test
  void testListProviderConfigParams_nullProviderSerializesEmptyObject() {
    ByokListProviderConfigParams params = new ByokListProviderConfigParams(null);

    JsonObject json = JsonParser.parseString(GSON.toJson(params)).getAsJsonObject();

    assertEquals(0, json.size());
  }

  @Test
  void testDeleteProviderConfigParams_serializesOnlyProviderName() {
    ByokDeleteProviderConfigParams params = new ByokDeleteProviderConfigParams("Ollama");

    JsonObject json = JsonParser.parseString(GSON.toJson(params)).getAsJsonObject();

    assertEquals(1, json.size());
    assertEquals("Ollama", json.get("providerName").getAsString());
  }

  @Test
  void testListProviderConfigResponse_deserializesClsResponse() {
    ByokListProviderConfigResponse response = GSON.fromJson(
        "{\"providers\":[{\"providerName\":\"Ollama\",\"url\":\"http://localhost:11434\"}]}",
        ByokListProviderConfigResponse.class);

    assertEquals(1, response.providers().size());
    assertEquals(new ByokProviderConfig("Ollama", "http://localhost:11434"), response.providers().get(0));
  }

  @Test
  void testCustomProviderConfig_serializesClsFieldNames() {
    ByokCustomProviderConfig config = new ByokCustomProviderConfig("LM Studio", "dummy-key", "LM Studio",
        "chatCompletions");

    JsonObject json = JsonParser.parseString(GSON.toJson(config)).getAsJsonObject();

    assertEquals("LM Studio", json.get("providerName").getAsString());
    assertEquals("dummy-key", json.get("apiKey").getAsString());
    assertEquals("LM Studio", json.get("groupName").getAsString());
    assertEquals("chatCompletions", json.get("apiType").getAsString());
  }

  @Test
  void testCustomProviderConfig_nullApiTypeOmitted() {
    ByokCustomProviderConfig config = new ByokCustomProviderConfig("vLLM", "key", "vLLM", null);

    JsonObject json = JsonParser.parseString(GSON.toJson(config)).getAsJsonObject();

    assertEquals(3, json.size());
  }

  @Test
  void testListCustomProviderConfigParams_nullProviderSerializesEmptyObject() {
    ByokListCustomProviderConfigParams params = new ByokListCustomProviderConfigParams(null);

    JsonObject json = JsonParser.parseString(GSON.toJson(params)).getAsJsonObject();

    assertEquals(0, json.size());
  }

  @Test
  void testListCustomProviderConfigResponse_deserializesClsResponse() {
    ByokListCustomProviderConfigResponse response = GSON.fromJson(
        "{\"providers\":[{\"providerName\":\"LM Studio\",\"groupName\":\"LM Studio\",\"apiType\":\"chatCompletions\"}]}",
        ByokListCustomProviderConfigResponse.class);

    assertEquals(1, response.providers().size());
    assertEquals(new ByokCustomProviderInfo("LM Studio", "LM Studio", "chatCompletions"), response.providers().get(0));
  }
}
