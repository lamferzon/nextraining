/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { Prova1500mComponentsPage, Prova1500mDeleteDialog, Prova1500mUpdatePage } from './prova-1500-m.page-object';

const expect = chai.expect;

describe('Prova1500m e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let prova1500mUpdatePage: Prova1500mUpdatePage;
  let prova1500mComponentsPage: Prova1500mComponentsPage;
  /*let prova1500mDeleteDialog: Prova1500mDeleteDialog;*/

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Prova1500ms', async () => {
    await navBarPage.goToEntity('prova-1500-m');
    prova1500mComponentsPage = new Prova1500mComponentsPage();
    await browser.wait(ec.visibilityOf(prova1500mComponentsPage.title), 5000);
    expect(await prova1500mComponentsPage.getTitle()).to.eq('nextrainingApp.prova1500m.home.title');
  });

  it('should load create Prova1500m page', async () => {
    await prova1500mComponentsPage.clickOnCreateButton();
    prova1500mUpdatePage = new Prova1500mUpdatePage();
    expect(await prova1500mUpdatePage.getPageTitle()).to.eq('nextrainingApp.prova1500m.home.createOrEditLabel');
    await prova1500mUpdatePage.cancel();
  });

  /* it('should create and save Prova1500ms', async () => {
        const nbButtonsBeforeCreate = await prova1500mComponentsPage.countDeleteButtons();

        await prova1500mComponentsPage.clickOnCreateButton();
        await promise.all([
            prova1500mUpdatePage.setDataProvaInput('2000-12-31'),
            prova1500mUpdatePage.setTempoInput('5'),
            prova1500mUpdatePage.setTempoKmInput('5'),
            prova1500mUpdatePage.commentoSelectLastOption(),
            prova1500mUpdatePage.setCondClimaticheInput('condClimatiche'),
            prova1500mUpdatePage.calciatoreSelectLastOption(),
        ]);
        expect(await prova1500mUpdatePage.getDataProvaInput()).to.eq('2000-12-31', 'Expected dataProva value to be equals to 2000-12-31');
        expect(await prova1500mUpdatePage.getTempoInput()).to.eq('5', 'Expected tempo value to be equals to 5');
        expect(await prova1500mUpdatePage.getTempoKmInput()).to.eq('5', 'Expected tempoKm value to be equals to 5');
        expect(await prova1500mUpdatePage.getCondClimaticheInput()).to.eq('condClimatiche', 'Expected CondClimatiche value to be equals to condClimatiche');
        await prova1500mUpdatePage.save();
        expect(await prova1500mUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await prova1500mComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    });*/

  /* it('should delete last Prova1500m', async () => {
        const nbButtonsBeforeDelete = await prova1500mComponentsPage.countDeleteButtons();
        await prova1500mComponentsPage.clickOnLastDeleteButton();

        prova1500mDeleteDialog = new Prova1500mDeleteDialog();
        expect(await prova1500mDeleteDialog.getDialogTitle())
            .to.eq('nextrainingApp.prova1500m.delete.question');
        await prova1500mDeleteDialog.clickOnConfirmButton();

        expect(await prova1500mComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });*/

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
