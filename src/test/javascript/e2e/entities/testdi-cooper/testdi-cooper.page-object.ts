import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class TestdiCooperComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-testdi-cooper div table .btn-danger'));
  title = element.all(by.css('jhi-testdi-cooper div h2#page-heading span')).first();

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

export class TestdiCooperUpdatePage {
  pageTitle = element(by.id('jhi-testdi-cooper-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  dataTestInput = element(by.id('field_dataTest'));
  distanzaInput = element(by.id('field_distanza'));
  v02MaxInput = element(by.id('field_v02Max'));
  commentoSelect = element(by.id('field_commento'));
  condClimaticheInput = element(by.id('field_condClimatiche'));
  calciatoreSelect = element(by.id('field_calciatore'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setDataTestInput(dataTest) {
    await this.dataTestInput.sendKeys(dataTest);
  }

  async getDataTestInput() {
    return await this.dataTestInput.getAttribute('value');
  }

  async setDistanzaInput(distanza) {
    await this.distanzaInput.sendKeys(distanza);
  }

  async getDistanzaInput() {
    return await this.distanzaInput.getAttribute('value');
  }

  async setV02MaxInput(v02Max) {
    await this.v02MaxInput.sendKeys(v02Max);
  }

  async getV02MaxInput() {
    return await this.v02MaxInput.getAttribute('value');
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

export class TestdiCooperDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-testdiCooper-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-testdiCooper'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
