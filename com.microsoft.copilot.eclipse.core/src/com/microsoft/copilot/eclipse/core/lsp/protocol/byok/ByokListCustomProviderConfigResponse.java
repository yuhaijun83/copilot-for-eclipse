// Copyright (c) Microsoft Corporation.
// Licensed under the MIT license.

package com.microsoft.copilot.eclipse.core.lsp.protocol.byok;

import java.util.List;

/**
 * Response model for listing custom BYOK endpoint provider configurations.
 *
 * @param providers custom provider configurations
 */
public record ByokListCustomProviderConfigResponse(List<ByokCustomProviderInfo> providers) {
}
