/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { TestdiCooperComponentsPage, TestdiCooperDeleteDialog, TestdiCooperUpdatePage } from './testdi-cooper.page-object';

const expect = chai.expect;

describe('TestdiCooper e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let testdiCooperUpdatePage: TestdiCooperUpdatePage;
  let testdiCooperComponentsPage: TestdiCooperComponentsPage;
  /*let testdiCooperDeleteDialog: TestdiCooperDeleteDialog;*/

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load TestdiCoopers', async () => {
    await navBarPage.goToEntity('testdi-cooper');
    testdiCooperComponentsPage = new TestdiCooperComponentsPage();
    await browser.wait(ec.visibilityOf(testdiCooperComponentsPage.title), 5000);
    expect(await testdiCooperComponentsPage.getTitle()).to.eq('nextrainingApp.testdiCooper.home.title');
  });

  it('should load create TestdiCooper page', async () => {
    await testdiCooperComponentsPage.clickOnCreateButton();
    testdiCooperUpdatePage = new TestdiCooperUpdatePage();
    expect(await testdiCooperUpdatePage.getPageTitle()).to.eq('nextrainingApp.testdiCooper.home.createOrEditLabel');
    await testdiCooperUpdatePage.cancel();
  });

  /* it('should create and save TestdiCoopers', async () => {
        const nbButtonsBeforeCreate = await testdiCooperComponentsPage.countDeleteButtons();

        await testdiCooperComponentsPage.clickOnCreateButton();
        await promise.all([
            testdiCooperUpdatePage.setDataTestInput('2000-12-31'),
            testdiCooperUpdatePage.setDistanzaInput('5'),
            testdiCooperUpdatePage.setV02MaxInput('5'),
            testdiCooperUpdatePage.commentoSelectLastOption(),
            testdiCooperUpdatePage.setCondClimaticheInput('condClimatiche'),
            testdiCooperUpdatePage.calciatoreSelectLastOption(),
        ]);
        expect(await testdiCooperUpdatePage.getDataTestInput()).to.eq('2000-12-31', 'Expected dataTest value to be equals to 2000-12-31');
        expect(await testdiCooperUpdatePage.getDistanzaInput()).to.eq('5', 'Expected distanza value to be equals to 5');
        expect(await testdiCooperUpdatePage.getV02MaxInput()).to.eq('5', 'Expected v02Max value to be equals to 5');
        expect(await testdiCooperUpdatePage.getCondClimaticheInput()).to.eq('condClimatiche', 'Expected CondClimatiche value to be equals to condClimatiche');
        await testdiCooperUpdatePage.save();
        expect(await testdiCooperUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await testdiCooperComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    });*/

  /* it('should delete last TestdiCooper', async () => {
        const nbButtonsBeforeDelete = await testdiCooperComponentsPage.countDeleteButtons();
        await testdiCooperComponentsPage.clickOnLastDeleteButton();

        testdiCooperDeleteDialog = new TestdiCooperDeleteDialog();
        expect(await testdiCooperDeleteDialog.getDialogTitle())
            .to.eq('nextrainingApp.testdiCooper.delete.question');
        await testdiCooperDeleteDialog.clickOnConfirmButton();

        expect(await testdiCooperComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });*/

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
