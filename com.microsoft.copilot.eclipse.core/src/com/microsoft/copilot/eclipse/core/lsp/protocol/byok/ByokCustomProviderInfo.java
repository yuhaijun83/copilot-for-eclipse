// Copyright (c) Microsoft Corporation.
// Licensed under the MIT license.

package com.microsoft.copilot.eclipse.core.lsp.protocol.byok;

import org.eclipse.jdt.annotation.Nullable;

/**
 * A custom (user-named) BYOK endpoint provider configuration as returned by the language server.
 *
 * @param providerName user-defined provider display name
 * @param groupName    display group for the provider
 * @param apiType      wire API type: {@code chatCompletions}, {@code responses} or {@code messages}
 */
public record ByokCustomProviderInfo(String providerName, @Nullable String groupName, @Nullable String apiType) {
}
