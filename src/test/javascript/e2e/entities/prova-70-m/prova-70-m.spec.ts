/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { Prova70mComponentsPage, Prova70mDeleteDialog, Prova70mUpdatePage } from './prova-70-m.page-object';

const expect = chai.expect;

describe('Prova70m e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let prova70mUpdatePage: Prova70mUpdatePage;
  let prova70mComponentsPage: Prova70mComponentsPage;
  /*let prova70mDeleteDialog: Prova70mDeleteDialog;*/

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Prova70ms', async () => {
    await navBarPage.goToEntity('prova-70-m');
    prova70mComponentsPage = new Prova70mComponentsPage();
    await browser.wait(ec.visibilityOf(prova70mComponentsPage.title), 5000);
    expect(await prova70mComponentsPage.getTitle()).to.eq('nextrainingApp.prova70m.home.title');
  });

  it('should load create Prova70m page', async () => {
    await prova70mComponentsPage.clickOnCreateButton();
    prova70mUpdatePage = new Prova70mUpdatePage();
    expect(await prova70mUpdatePage.getPageTitle()).to.eq('nextrainingApp.prova70m.home.createOrEditLabel');
    await prova70mUpdatePage.cancel();
  });

  /* it('should create and save Prova70ms', async () => {
        const nbButtonsBeforeCreate = await prova70mComponentsPage.countDeleteButtons();

        await prova70mComponentsPage.clickOnCreateButton();
        await promise.all([
            prova70mUpdatePage.setDataProvaInput('2000-12-31'),
            prova70mUpdatePage.setTempoInput('5'),
            prova70mUpdatePage.setVelMaxInput('5'),
            prova70mUpdatePage.commentoSelectLastOption(),
            prova70mUpdatePage.setCondClimaticheInput('condClimatiche'),
            prova70mUpdatePage.calciatoreSelectLastOption(),
        ]);
        expect(await prova70mUpdatePage.getDataProvaInput()).to.eq('2000-12-31', 'Expected dataProva value to be equals to 2000-12-31');
        expect(await prova70mUpdatePage.getTempoInput()).to.eq('5', 'Expected tempo value to be equals to 5');
        const selectedPartenzaBlocchi = prova70mUpdatePage.getPartenzaBlocchiInput();
        if (await selectedPartenzaBlocchi.isSelected()) {
            await prova70mUpdatePage.getPartenzaBlocchiInput().click();
            expect(await prova70mUpdatePage.getPartenzaBlocchiInput().isSelected(), 'Expected partenzaBlocchi not to be selected').to.be.false;
        } else {
            await prova70mUpdatePage.getPartenzaBlocchiInput().click();
            expect(await prova70mUpdatePage.getPartenzaBlocchiInput().isSelected(), 'Expected partenzaBlocchi to be selected').to.be.true;
        }
        expect(await prova70mUpdatePage.getVelMaxInput()).to.eq('5', 'Expected velMax value to be equals to 5');
        expect(await prova70mUpdatePage.getCondClimaticheInput()).to.eq('condClimatiche', 'Expected CondClimatiche value to be equals to condClimatiche');
        await prova70mUpdatePage.save();
        expect(await prova70mUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await prova70mComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    });*/

  /* it('should delete last Prova70m', async () => {
        const nbButtonsBeforeDelete = await prova70mComponentsPage.countDeleteButtons();
        await prova70mComponentsPage.clickOnLastDeleteButton();

        prova70mDeleteDialog = new Prova70mDeleteDialog();
        expect(await prova70mDeleteDialog.getDialogTitle())
            .to.eq('nextrainingApp.prova70m.delete.question');
        await prova70mDeleteDialog.clickOnConfirmButton();

        expect(await prova70mComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });*/

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
