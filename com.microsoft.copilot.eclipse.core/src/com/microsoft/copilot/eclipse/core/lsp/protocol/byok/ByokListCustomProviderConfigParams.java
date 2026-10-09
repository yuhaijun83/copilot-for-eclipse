// Copyright (c) Microsoft Corporation.
// Licensed under the MIT license.

package com.microsoft.copilot.eclipse.core.lsp.protocol.byok;

import org.eclipse.jdt.annotation.Nullable;

/**
 * Parameters for listing custom BYOK endpoint provider configurations.
 *
 * @param providerName provider name, or {@code null} to list all configured custom providers
 */
public record ByokListCustomProviderConfigParams(@Nullable String providerName) {
}
