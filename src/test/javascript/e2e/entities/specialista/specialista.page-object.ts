import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class SpecialistaComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-specialista div table .btn-danger'));
  title = element.all(by.css('jhi-specialista div h2#page-heading span')).first();

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

export class SpecialistaUpdatePage {
  pageTitle = element(by.id('jhi-specialista-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  codFiscaleInput = element(by.id('field_codFiscale'));
  cognomeInput = element(by.id('field_cognome'));
  nomeInput = element(by.id('field_nome'));
  specializzazioneInput = element(by.id('field_specializzazione'));
  numTelefonoInput = element(by.id('field_numTelefono'));
  emailInput = element(by.id('field_email'));
  indirizzoStudioInput = element(by.id('field_indirizzoStudio'));
  paeseStudioInput = element(by.id('field_paeseStudio'));

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

  async setSpecializzazioneInput(specializzazione) {
    await this.specializzazioneInput.sendKeys(specializzazione);
  }

  async getSpecializzazioneInput() {
    return await this.specializzazioneInput.getAttribute('value');
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

  async setIndirizzoStudioInput(indirizzoStudio) {
    await this.indirizzoStudioInput.sendKeys(indirizzoStudio);
  }

  async getIndirizzoStudioInput() {
    return await this.indirizzoStudioInput.getAttribute('value');
  }

  async setPaeseStudioInput(paeseStudio) {
    await this.paeseStudioInput.sendKeys(paeseStudio);
  }

  async getPaeseStudioInput() {
    return await this.paeseStudioInput.getAttribute('value');
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

export class SpecialistaDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-specialista-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-specialista'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
