// Copyright (c) Microsoft Corporation.
// Licensed under the MIT license.

package com.microsoft.copilot.eclipse.core.lsp.protocol.byok;

import org.eclipse.jdt.annotation.Nullable;

/**
 * Parameters for saving a custom (user-named) BYOK endpoint provider configuration. The provider name must not
 * collide with a built-in provider name.
 *
 * @param providerName user-defined provider display name
 * @param apiKey       provider API key; must not be empty (use a placeholder for key-less local servers)
 * @param groupName    display group for the provider
 * @param apiType      wire API type: {@code chatCompletions}, {@code responses} or {@code messages}; {@code null}
 *                     lets the language server default to {@code chatCompletions}
 */
public record ByokCustomProviderConfig(String providerName, String apiKey, String groupName, @Nullable String apiType) {
}
