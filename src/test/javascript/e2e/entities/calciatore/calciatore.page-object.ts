import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class CalciatoreComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-calciatore div table .btn-danger'));
  title = element.all(by.css('jhi-calciatore div h2#page-heading span')).first();

  async clickOnCreateButton(timeout?: number) {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(timeout?: number) {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons() {
    return this.deleteButtons.count();
  }

  async getTitle() {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class CalciatoreUpdatePage {
  pageTitle = element(by.id('jhi-calciatore-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  codFiscaleInput = element(by.id('field_codFiscale'));
  cognomeInput = element(by.id('field_cognome'));
  nomeInput = element(by.id('field_nome'));
  dataNascitaInput = element(by.id('field_dataNascita'));
  numTelefonoInput = element(by.id('field_numTelefono'));
  emailInput = element(by.id('field_email'));
  repartoSelect = element(by.id('field_reparto'));
  ruoloSelect = element(by.id('field_ruolo'));
  selettoreSelect = element(by.id('field_selettore'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setCodFiscaleInput(codFiscale) {
    await this.codFiscaleInput.sendKeys(codFiscale);
  }

  async getCodFiscaleInput() {
    return await this.codFiscaleInput.getAttribute('value');
  }

  async setCognomeInput(cognome) {
    await this.cognomeInput.sendKeys(cognome);
  }

  async getCognomeInput() {
    return await this.cognomeInput.getAttribute('value');
  }

  async setNomeInput(nome) {
    await this.nomeInput.sendKeys(nome);
  }

  async getNomeInput() {
    return await this.nomeInput.getAttribute('value');
  }

  async setDataNascitaInput(dataNascita) {
    await this.dataNascitaInput.sendKeys(dataNascita);
  }

  async getDataNascitaInput() {
    return await this.dataNascitaInput.getAttribute('value');
  }

  async setNumTelefonoInput(numTelefono) {
    await this.numTelefonoInput.sendKeys(numTelefono);
  }

  async getNumTelefonoInput() {
    return await this.numTelefonoInput.getAttribute('value');
  }

  async setEmailInput(email) {
    await this.emailInput.sendKeys(email);
  }

  async getEmailInput() {
    return await this.emailInput.getAttribute('value');
  }

  async setRepartoSelect(reparto) {
    await this.repartoSelect.sendKeys(reparto);
  }

  async getRepartoSelect() {
    return await this.repartoSelect.element(by.css('option:checked')).getText();
  }

  async repartoSelectLastOption(timeout?: number) {
    await this.repartoSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setRuoloSelect(ruolo) {
    await this.ruoloSelect.sendKeys(ruolo);
  }

  async getRuoloSelect() {
    return await this.ruoloSelect.element(by.css('option:checked')).getText();
  }

  async ruoloSelectLastOption(timeout?: number) {
    await this.ruoloSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setSelettoreSelect(selettore) {
    await this.selettoreSelect.sendKeys(selettore);
  }

  async getSelettoreSelect() {
    return await this.selettoreSelect.element(by.css('option:checked')).getText();
  }

  async selettoreSelectLastOption(timeout?: number) {
    await this.selettoreSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async save(timeout?: number) {
    await this.saveButton.click();
  }

  async cancel(timeout?: number) {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class CalciatoreDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-calciatore-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-calciatore'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
