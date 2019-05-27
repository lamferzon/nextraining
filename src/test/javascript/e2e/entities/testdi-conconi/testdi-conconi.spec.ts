/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { TestdiConconiComponentsPage, TestdiConconiDeleteDialog, TestdiConconiUpdatePage } from './testdi-conconi.page-object';

const expect = chai.expect;

describe('TestdiConconi e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let testdiConconiUpdatePage: TestdiConconiUpdatePage;
  let testdiConconiComponentsPage: TestdiConconiComponentsPage;
  /*let testdiConconiDeleteDialog: TestdiConconiDeleteDialog;*/

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load TestdiConconis', async () => {
    await navBarPage.goToEntity('testdi-conconi');
    testdiConconiComponentsPage = new TestdiConconiComponentsPage();
    await browser.wait(ec.visibilityOf(testdiConconiComponentsPage.title), 5000);
    expect(await testdiConconiComponentsPage.getTitle()).to.eq('nextrainingApp.testdiConconi.home.title');
  });

  it('should load create TestdiConconi page', async () => {
    await testdiConconiComponentsPage.clickOnCreateButton();
    testdiConconiUpdatePage = new TestdiConconiUpdatePage();
    expect(await testdiConconiUpdatePage.getPageTitle()).to.eq('nextrainingApp.testdiConconi.home.createOrEditLabel');
    await testdiConconiUpdatePage.cancel();
  });

  /* it('should create and save TestdiConconis', async () => {
        const nbButtonsBeforeCreate = await testdiConconiComponentsPage.countDeleteButtons();

        await testdiConconiComponentsPage.clickOnCreateButton();
        await promise.all([
            testdiConconiUpdatePage.setFcMaxInput('5'),
            testdiConconiUpdatePage.setSogliaAnaerobicaInput('5'),
            testdiConconiUpdatePage.setVelSogliaInput('5'),
            testdiConconiUpdatePage.setDurataInput('5'),
            testdiConconiUpdatePage.commentoSelectLastOption(),
            testdiConconiUpdatePage.setCondClimaticheInput('condClimatiche'),
            testdiConconiUpdatePage.calciatoreSelectLastOption(),
        ]);
        expect(await testdiConconiUpdatePage.getFcMaxInput()).to.eq('5', 'Expected fcMax value to be equals to 5');
        expect(await testdiConconiUpdatePage.getSogliaAnaerobicaInput()).to.eq('5', 'Expected sogliaAnaerobica value to be equals to 5');
        expect(await testdiConconiUpdatePage.getVelSogliaInput()).to.eq('5', 'Expected velSoglia value to be equals to 5');
        expect(await testdiConconiUpdatePage.getDurataInput()).to.eq('5', 'Expected durata value to be equals to 5');
        expect(await testdiConconiUpdatePage.getCondClimaticheInput()).to.eq('condClimatiche', 'Expected CondClimatiche value to be equals to condClimatiche');
        await testdiConconiUpdatePage.save();
        expect(await testdiConconiUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await testdiConconiComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    });*/

  /* it('should delete last TestdiConconi', async () => {
        const nbButtonsBeforeDelete = await testdiConconiComponentsPage.countDeleteButtons();
        await testdiConconiComponentsPage.clickOnLastDeleteButton();

        testdiConconiDeleteDialog = new TestdiConconiDeleteDialog();
        expect(await testdiConconiDeleteDialog.getDialogTitle())
            .to.eq('nextrainingApp.testdiConconi.delete.question');
        await testdiConconiDeleteDialog.clickOnConfirmButton();

        expect(await testdiConconiComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });*/

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
