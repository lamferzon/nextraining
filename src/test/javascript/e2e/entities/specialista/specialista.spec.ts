/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { SpecialistaComponentsPage, SpecialistaDeleteDialog, SpecialistaUpdatePage } from './specialista.page-object';

const expect = chai.expect;

describe('Specialista e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let specialistaUpdatePage: SpecialistaUpdatePage;
  let specialistaComponentsPage: SpecialistaComponentsPage;
  let specialistaDeleteDialog: SpecialistaDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Specialistas', async () => {
    await navBarPage.goToEntity('specialista');
    specialistaComponentsPage = new SpecialistaComponentsPage();
    await browser.wait(ec.visibilityOf(specialistaComponentsPage.title), 5000);
    expect(await specialistaComponentsPage.getTitle()).to.eq('nextrainingApp.specialista.home.title');
  });

  it('should load create Specialista page', async () => {
    await specialistaComponentsPage.clickOnCreateButton();
    specialistaUpdatePage = new SpecialistaUpdatePage();
    expect(await specialistaUpdatePage.getPageTitle()).to.eq('nextrainingApp.specialista.home.createOrEditLabel');
    await specialistaUpdatePage.cancel();
  });

  it('should create and save Specialistas', async () => {
    const nbButtonsBeforeCreate = await specialistaComponentsPage.countDeleteButtons();

    await specialistaComponentsPage.clickOnCreateButton();
    await promise.all([
      specialistaUpdatePage.setCodFiscaleInput('codFiscale'),
      specialistaUpdatePage.setCognomeInput('cognome'),
      specialistaUpdatePage.setNomeInput('nome'),
      specialistaUpdatePage.setSpecializzazioneInput('specializzazione'),
      specialistaUpdatePage.setNumTelefonoInput('numTelefono'),
      specialistaUpdatePage.setEmailInput('email'),
      specialistaUpdatePage.setIndirizzoStudioInput('indirizzoStudio'),
      specialistaUpdatePage.setPaeseStudioInput('paeseStudio')
    ]);
    expect(await specialistaUpdatePage.getCodFiscaleInput()).to.eq('codFiscale', 'Expected CodFiscale value to be equals to codFiscale');
    expect(await specialistaUpdatePage.getCognomeInput()).to.eq('cognome', 'Expected Cognome value to be equals to cognome');
    expect(await specialistaUpdatePage.getNomeInput()).to.eq('nome', 'Expected Nome value to be equals to nome');
    expect(await specialistaUpdatePage.getSpecializzazioneInput()).to.eq(
      'specializzazione',
      'Expected Specializzazione value to be equals to specializzazione'
    );
    expect(await specialistaUpdatePage.getNumTelefonoInput()).to.eq(
      'numTelefono',
      'Expected NumTelefono value to be equals to numTelefono'
    );
    expect(await specialistaUpdatePage.getEmailInput()).to.eq('email', 'Expected Email value to be equals to email');
    expect(await specialistaUpdatePage.getIndirizzoStudioInput()).to.eq(
      'indirizzoStudio',
      'Expected IndirizzoStudio value to be equals to indirizzoStudio'
    );
    expect(await specialistaUpdatePage.getPaeseStudioInput()).to.eq(
      'paeseStudio',
      'Expected PaeseStudio value to be equals to paeseStudio'
    );
    await specialistaUpdatePage.save();
    expect(await specialistaUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await specialistaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Specialista', async () => {
    const nbButtonsBeforeDelete = await specialistaComponentsPage.countDeleteButtons();
    await specialistaComponentsPage.clickOnLastDeleteButton();

    specialistaDeleteDialog = new SpecialistaDeleteDialog();
    expect(await specialistaDeleteDialog.getDialogTitle()).to.eq('nextrainingApp.specialista.delete.question');
    await specialistaDeleteDialog.clickOnConfirmButton();

    expect(await specialistaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
