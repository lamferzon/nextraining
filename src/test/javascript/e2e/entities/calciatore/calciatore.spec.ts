/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { CalciatoreComponentsPage, CalciatoreDeleteDialog, CalciatoreUpdatePage } from './calciatore.page-object';

const expect = chai.expect;

describe('Calciatore e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let calciatoreUpdatePage: CalciatoreUpdatePage;
  let calciatoreComponentsPage: CalciatoreComponentsPage;
  let calciatoreDeleteDialog: CalciatoreDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Calciatores', async () => {
    await navBarPage.goToEntity('calciatore');
    calciatoreComponentsPage = new CalciatoreComponentsPage();
    await browser.wait(ec.visibilityOf(calciatoreComponentsPage.title), 5000);
    expect(await calciatoreComponentsPage.getTitle()).to.eq('nextrainingApp.calciatore.home.title');
  });

  it('should load create Calciatore page', async () => {
    await calciatoreComponentsPage.clickOnCreateButton();
    calciatoreUpdatePage = new CalciatoreUpdatePage();
    expect(await calciatoreUpdatePage.getPageTitle()).to.eq('nextrainingApp.calciatore.home.createOrEditLabel');
    await calciatoreUpdatePage.cancel();
  });

  it('should create and save Calciatores', async () => {
    const nbButtonsBeforeCreate = await calciatoreComponentsPage.countDeleteButtons();

    await calciatoreComponentsPage.clickOnCreateButton();
    await promise.all([
      calciatoreUpdatePage.setCodFiscaleInput('codFiscale'),
      calciatoreUpdatePage.setCognomeInput('cognome'),
      calciatoreUpdatePage.setNomeInput('nome'),
      calciatoreUpdatePage.setDataNascitaInput('2000-12-31'),
      calciatoreUpdatePage.setNumTelefonoInput('numTelefono'),
      calciatoreUpdatePage.setEmailInput('email'),
      calciatoreUpdatePage.repartoSelectLastOption(),
      calciatoreUpdatePage.ruoloSelectLastOption(),
      calciatoreUpdatePage.selettoreSelectLastOption()
    ]);
    expect(await calciatoreUpdatePage.getCodFiscaleInput()).to.eq('codFiscale', 'Expected CodFiscale value to be equals to codFiscale');
    expect(await calciatoreUpdatePage.getCognomeInput()).to.eq('cognome', 'Expected Cognome value to be equals to cognome');
    expect(await calciatoreUpdatePage.getNomeInput()).to.eq('nome', 'Expected Nome value to be equals to nome');
    expect(await calciatoreUpdatePage.getDataNascitaInput()).to.eq('2000-12-31', 'Expected dataNascita value to be equals to 2000-12-31');
    expect(await calciatoreUpdatePage.getNumTelefonoInput()).to.eq('numTelefono', 'Expected NumTelefono value to be equals to numTelefono');
    expect(await calciatoreUpdatePage.getEmailInput()).to.eq('email', 'Expected Email value to be equals to email');
    await calciatoreUpdatePage.save();
    expect(await calciatoreUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await calciatoreComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Calciatore', async () => {
    const nbButtonsBeforeDelete = await calciatoreComponentsPage.countDeleteButtons();
    await calciatoreComponentsPage.clickOnLastDeleteButton();

    calciatoreDeleteDialog = new CalciatoreDeleteDialog();
    expect(await calciatoreDeleteDialog.getDialogTitle()).to.eq('nextrainingApp.calciatore.delete.question');
    await calciatoreDeleteDialog.clickOnConfirmButton();

    expect(await calciatoreComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
