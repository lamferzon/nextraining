import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class InfortunioComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-infortunio div table .btn-danger'));
  title = element.all(by.css('jhi-infortunio div h2#page-heading span')).first();

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

export class InfortunioUpdatePage {
  pageTitle = element(by.id('jhi-infortunio-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  dataInizioInput = element(by.id('field_dataInizio'));
  dataFineInput = element(by.id('field_dataFine'));
  gravitaSelect = element(by.id('field_gravita'));
  descrizioneInput = element(by.id('field_descrizione'));
  specialistaSelect = element(by.id('field_specialista'));
  calciatoreSelect = element(by.id('field_calciatore'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setDataInizioInput(dataInizio) {
    await this.dataInizioInput.sendKeys(dataInizio);
  }

  async getDataInizioInput() {
    return await this.dataInizioInput.getAttribute('value');
  }

  async setDataFineInput(dataFine) {
    await this.dataFineInput.sendKeys(dataFine);
  }

  async getDataFineInput() {
    return await this.dataFineInput.getAttribute('value');
  }

  async setGravitaSelect(gravita) {
    await this.gravitaSelect.sendKeys(gravita);
  }

  async getGravitaSelect() {
    return await this.gravitaSelect.element(by.css('option:checked')).getText();
  }

  async gravitaSelectLastOption(timeout?: number) {
    await this.gravitaSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setDescrizioneInput(descrizione) {
    await this.descrizioneInput.sendKeys(descrizione);
  }

  async getDescrizioneInput() {
    return await this.descrizioneInput.getAttribute('value');
  }

  async specialistaSelectLastOption(timeout?: number) {
    await this.specialistaSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async specialistaSelectOption(option) {
    await this.specialistaSelect.sendKeys(option);
  }

  getSpecialistaSelect(): ElementFinder {
    return this.specialistaSelect;
  }

  async getSpecialistaSelectedOption() {
    return await this.specialistaSelect.element(by.css('option:checked')).getText();
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

export class InfortunioDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-infortunio-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-infortunio'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
