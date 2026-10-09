// Copyright (c) Microsoft Corporation.
// Licensed under the MIT license.

package com.microsoft.copilot.eclipse.ui.preferences;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.function.Consumer;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.microsoft.copilot.eclipse.core.lsp.protocol.byok.ByokModelProvider;

/**
 * Dialog to register a custom (user-named) OpenAI-compatible endpoint provider, e.g. a local LM Studio, vLLM or
 * llama.cpp server.
 */
public class AddCustomProviderDialog extends TrayDialog {

  private static final int CONTAINER_WIDTH = 500;

  /**
   * User input collected by this dialog.
   *
   * @param providerName user-defined provider name
   * @param endpointUrl  base URL of the OpenAI-compatible server
   * @param apiKey       API key; blank when the server does not require one
   * @param apiType      wire API type: {@code chatCompletions}, {@code responses} or {@code messages}
   */
  public record CustomProviderInput(String providerName, String endpointUrl, String apiKey, String apiType) {
  }

  private static final String[] API_TYPE_VALUES = { "chatCompletions", "responses", "messages" };

  private Text providerNameText;
  private Text endpointText;
  private Text apiKeyText;
  private Combo apiTypeCombo;
  private Button addButton;

  private final Consumer<CustomProviderInput> onSave;

  /**
   * Create the dialog.
   *
   * @param parentShell parent shell
   * @param onSave      consumer invoked with the collected input when the user confirms
   */
  public AddCustomProviderDialog(Shell parentShell, Consumer<CustomProviderInput> onSave) {
    super(parentShell);
    this.onSave = onSave;
    setShellStyle(getShellStyle() | SWT.RESIZE);
  }

  @Override
  protected void configureShell(Shell newShell) {
    super.configureShell(newShell);
    newShell.setText(Messages.preferences_page_byok_customProvider_dialog_title);
  }

  @Override
  protected Control createDialogArea(Composite parent) {
    GridLayout layout = new GridLayout(2, false);
    layout.marginWidth = 20;
    layout.marginHeight = 20;
    layout.verticalSpacing = 15;
    Composite container = (Composite) super.createDialogArea(parent);
    container.setLayout(layout);
    GridData containerGd = new GridData(SWT.FILL, SWT.FILL, true, true);
    containerGd.widthHint = CONTAINER_WIDTH;
    container.setLayoutData(containerGd);

    new Label(container, SWT.NONE).setText(Messages.preferences_page_byok_customProvider_name);
    providerNameText = new Text(container, SWT.BORDER);
    providerNameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
    providerNameText.addModifyListener(event -> updateAddButtonState());

    new Label(container, SWT.NONE).setText(Messages.preferences_page_byok_customProvider_endpoint);
    endpointText = new Text(container, SWT.BORDER);
    endpointText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
    endpointText.addModifyListener(event -> updateAddButtonState());

    new Label(container, SWT.NONE).setText(Messages.preferences_page_byok_customProvider_apiKey);
    apiKeyText = new Text(container, SWT.BORDER);
    apiKeyText.setEchoChar('*');
    apiKeyText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

    Label hintLabel = new Label(container, SWT.WRAP);
    hintLabel.setText(Messages.preferences_page_byok_customProvider_apiKey_hint);
    GridData hintGd = new GridData(SWT.FILL, SWT.CENTER, true, false);
    hintGd.horizontalIndent = 5;
    hintLabel.setLayoutData(hintGd);

    new Label(container, SWT.NONE).setText(Messages.preferences_page_byok_customProvider_apiType);
    apiTypeCombo = new Combo(container, SWT.READ_ONLY);
    apiTypeCombo.setItems(new String[] { Messages.preferences_page_byok_customProvider_apiType_chatCompletions,
        Messages.preferences_page_byok_customProvider_apiType_responses,
        Messages.preferences_page_byok_customProvider_apiType_messages });
    apiTypeCombo.select(0);
    apiTypeCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

    return container;
  }

  @Override
  protected void createButtonsForButtonBar(Composite parent) {
    addButton = createButton(parent, IDialogConstants.OK_ID, Messages.preferences_page_byok_dialog_add, true);
    createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
    addButton.setEnabled(false);
  }

  private void updateAddButtonState() {
    if (addButton != null && !addButton.isDisposed()) {
      addButton.setEnabled(isValidInput());
    }
  }

  private boolean isValidInput() {
    return isValidProviderName(providerNameText.getText().trim()) && isValidEndpoint(endpointText.getText().trim());
  }

  /**
   * The language server treats any name that is not a built-in provider as a custom endpoint provider, so built-in
   * names must be rejected here.
   */
  private boolean isValidProviderName(String name) {
    return StringUtils.isNotBlank(name) && ByokModelProvider.isCustomProvider(name);
  }

  private boolean isValidEndpoint(String value) {
    try {
      URI uri = new URI(value);
      return ("http".equalsIgnoreCase(uri.getScheme()) || "https".equalsIgnoreCase(uri.getScheme()))
          && StringUtils.isNotBlank(uri.getHost());
    } catch (URISyntaxException e) {
      return false;
    }
  }

  @Override
  protected void okPressed() {
    if (!isValidInput()) {
      return;
    }
    if (onSave != null) {
      onSave.accept(new CustomProviderInput(providerNameText.getText().trim(), endpointText.getText().trim(),
          apiKeyText.getText().trim(), API_TYPE_VALUES[apiTypeCombo.getSelectionIndex()]));
    }
    super.okPressed();
  }
}
