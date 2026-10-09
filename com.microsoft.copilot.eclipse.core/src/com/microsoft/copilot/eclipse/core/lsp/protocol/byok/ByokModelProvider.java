// Copyright (c) Microsoft Corporation.
// Licensed under the MIT license.

package com.microsoft.copilot.eclipse.core.lsp.protocol.byok;

/**
 * Enum representing BYOK model providers.
 */
public enum ByokModelProvider {
  AZURE("Azure"),
  OPENAI("OpenAI"),
  GEMINI("Gemini"),
  GROQ("Groq"),
  OPENROUTER("OpenRouter"),
  ANTHROPIC("Anthropic"),
  OLLAMA("Ollama");


  private final String displayName;

  ByokModelProvider(String displayName) {
    this.displayName = displayName;
  }

  public String getDisplayName() {
    return displayName;
  }

  /**
   * Utility to check if a provider display name corresponds to AZURE.
   * This avoids scattering direct enum displayName comparisons across UI code.
   */
  public static boolean isAzure(String providerDisplayName) {
    return AZURE.getDisplayName().equals(providerDisplayName);
  }

  /**
   * Utility to check if a provider display name corresponds to Ollama.
   */
  public static boolean isOllama(String providerDisplayName) {
    return OLLAMA.getDisplayName().equals(providerDisplayName);
  }

  /**
   * Returns whether the provider requires a provider-level API key.
   */
  public static boolean requiresApiKey(String providerDisplayName) {
    return !isAzure(providerDisplayName) && !isOllama(providerDisplayName);
  }

  /**
   * Returns whether the given name refers to a user-defined custom endpoint provider, i.e. any name that is not
   * reserved by a built-in provider.
   */
  public static boolean isCustomProvider(String providerDisplayName) {
    if (providerDisplayName == null) {
      return false;
    }
    for (ByokModelProvider provider : values()) {
      if (provider.displayName.equals(providerDisplayName)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public String toString() {
    return displayName;
  }
}
