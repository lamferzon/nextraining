import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class AllenamentoAggiuntivoComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-allenamento-aggiuntivo div table .btn-danger'));
  title = element.all(by.css('jhi-allenamento-aggiuntivo div h2#page-heading span')).first();

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

export class AllenamentoAggiuntivoUpdatePage {
  pageTitle = element(by.id('jhi-allenamento-aggiuntivo-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  sportInput = element(by.id('field_sport'));
  naturaSelect = element(by.id('field_natura'));
  lavoroInput = element(by.id('field_lavoro'));
  durataInput = element(by.id('field_durata'));
  calciatoreSelect = element(by.id('field_calciatore'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setSportInput(sport) {
    await this.sportInput.sendKeys(sport);
  }

  async getSportInput() {
    return await this.sportInput.getAttribute('value');
  }

  async setNaturaSelect(natura) {
    await this.naturaSelect.sendKeys(natura);
  }

  async getNaturaSelect() {
    return await this.naturaSelect.element(by.css('option:checked')).getText();
  }

  async naturaSelectLastOption(timeout?: number) {
    await this.naturaSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setLavoroInput(lavoro) {
    await this.lavoroInput.sendKeys(lavoro);
  }

  async getLavoroInput() {
    return await this.lavoroInput.getAttribute('value');
  }

  async setDurataInput(durata) {
    await this.durataInput.sendKeys(durata);
  }

  async getDurataInput() {
    return await this.durataInput.getAttribute('value');
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

export class AllenamentoAggiuntivoDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-allenamentoAggiuntivo-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-allenamentoAggiuntivo'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
