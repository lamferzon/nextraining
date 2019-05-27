import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class TestdiConconiComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-testdi-conconi div table .btn-danger'));
  title = element.all(by.css('jhi-testdi-conconi div h2#page-heading span')).first();

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

export class TestdiConconiUpdatePage {
  pageTitle = element(by.id('jhi-testdi-conconi-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  fcMaxInput = element(by.id('field_fcMax'));
  sogliaAnaerobicaInput = element(by.id('field_sogliaAnaerobica'));
  velSogliaInput = element(by.id('field_velSoglia'));
  durataInput = element(by.id('field_durata'));
  commentoSelect = element(by.id('field_commento'));
  condClimaticheInput = element(by.id('field_condClimatiche'));
  calciatoreSelect = element(by.id('field_calciatore'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setFcMaxInput(fcMax) {
    await this.fcMaxInput.sendKeys(fcMax);
  }

  async getFcMaxInput() {
    return await this.fcMaxInput.getAttribute('value');
  }

  async setSogliaAnaerobicaInput(sogliaAnaerobica) {
    await this.sogliaAnaerobicaInput.sendKeys(sogliaAnaerobica);
  }

  async getSogliaAnaerobicaInput() {
    return await this.sogliaAnaerobicaInput.getAttribute('value');
  }

  async setVelSogliaInput(velSoglia) {
    await this.velSogliaInput.sendKeys(velSoglia);
  }

  async getVelSogliaInput() {
    return await this.velSogliaInput.getAttribute('value');
  }

  async setDurataInput(durata) {
    await this.durataInput.sendKeys(durata);
  }

  async getDurataInput() {
    return await this.durataInput.getAttribute('value');
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

export class TestdiConconiDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-testdiConconi-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-testdiConconi'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
