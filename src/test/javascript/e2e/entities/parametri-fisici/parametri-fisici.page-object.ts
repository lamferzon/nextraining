import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class ParametriFisiciComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-parametri-fisici div table .btn-danger'));
  title = element.all(by.css('jhi-parametri-fisici div h2#page-heading span')).first();

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

export class ParametriFisiciUpdatePage {
  pageTitle = element(by.id('jhi-parametri-fisici-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  dataRivelazioneInput = element(by.id('field_dataRivelazione'));
  massaCorporeaInput = element(by.id('field_massaCorporea'));
  altezzaInput = element(by.id('field_altezza'));
  bmiInput = element(by.id('field_bmi'));
  condizioneSelect = element(by.id('field_condizione'));
  fcRiposoInput = element(by.id('field_fcRiposo'));
  calciatoreSelect = element(by.id('field_calciatore'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setDataRivelazioneInput(dataRivelazione) {
    await this.dataRivelazioneInput.sendKeys(dataRivelazione);
  }

  async getDataRivelazioneInput() {
    return await this.dataRivelazioneInput.getAttribute('value');
  }

  async setMassaCorporeaInput(massaCorporea) {
    await this.massaCorporeaInput.sendKeys(massaCorporea);
  }

  async getMassaCorporeaInput() {
    return await this.massaCorporeaInput.getAttribute('value');
  }

  async setAltezzaInput(altezza) {
    await this.altezzaInput.sendKeys(altezza);
  }

  async getAltezzaInput() {
    return await this.altezzaInput.getAttribute('value');
  }

  async setBmiInput(bmi) {
    await this.bmiInput.sendKeys(bmi);
  }

  async getBmiInput() {
    return await this.bmiInput.getAttribute('value');
  }

  async setCondizioneSelect(condizione) {
    await this.condizioneSelect.sendKeys(condizione);
  }

  async getCondizioneSelect() {
    return await this.condizioneSelect.element(by.css('option:checked')).getText();
  }

  async condizioneSelectLastOption(timeout?: number) {
    await this.condizioneSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setFcRiposoInput(fcRiposo) {
    await this.fcRiposoInput.sendKeys(fcRiposo);
  }

  async getFcRiposoInput() {
    return await this.fcRiposoInput.getAttribute('value');
  }

  async calciatoreSelectLastOption(timeout?: number) {
    await this.calciatoreSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async calciatoreSelectOption(option) {
    await this.calciatoreSelect.sendKeys(option);
  }

  getCalciatoreSelect(): ElementFinder {
    return this.calciatoreSelect;
  }

  async getCalciatoreSelectedOption() {
    return await this.calciatoreSelect.element(by.css('option:checked')).getText();
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

export class ParametriFisiciDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-parametriFisici-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-parametriFisici'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
