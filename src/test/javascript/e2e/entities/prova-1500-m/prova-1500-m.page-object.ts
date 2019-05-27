import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class Prova1500mComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-prova-1500-m div table .btn-danger'));
  title = element.all(by.css('jhi-prova-1500-m div h2#page-heading span')).first();

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

export class Prova1500mUpdatePage {
  pageTitle = element(by.id('jhi-prova-1500-m-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  dataProvaInput = element(by.id('field_dataProva'));
  tempoInput = element(by.id('field_tempo'));
  tempoKmInput = element(by.id('field_tempoKm'));
  commentoSelect = element(by.id('field_commento'));
  condClimaticheInput = element(by.id('field_condClimatiche'));
  calciatoreSelect = element(by.id('field_calciatore'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setDataProvaInput(dataProva) {
    await this.dataProvaInput.sendKeys(dataProva);
  }

  async getDataProvaInput() {
    return await this.dataProvaInput.getAttribute('value');
  }

  async setTempoInput(tempo) {
    await this.tempoInput.sendKeys(tempo);
  }

  async getTempoInput() {
    return await this.tempoInput.getAttribute('value');
  }

  async setTempoKmInput(tempoKm) {
    await this.tempoKmInput.sendKeys(tempoKm);
  }

  async getTempoKmInput() {
    return await this.tempoKmInput.getAttribute('value');
  }

  async setCommentoSelect(commento) {
    await this.commentoSelect.sendKeys(commento);
  }

  async getCommentoSelect() {
    return await this.commentoSelect.element(by.css('option:checked')).getText();
  }

  async commentoSelectLastOption(timeout?: number) {
    await this.commentoSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setCondClimaticheInput(condClimatiche) {
    await this.condClimaticheInput.sendKeys(condClimatiche);
  }

  async getCondClimaticheInput() {
    return await this.condClimaticheInput.getAttribute('value');
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

export class Prova1500mDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-prova1500m-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-prova1500m'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
