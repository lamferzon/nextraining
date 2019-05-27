/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { InfortunioComponentsPage, InfortunioDeleteDialog, InfortunioUpdatePage } from './infortunio.page-object';

const expect = chai.expect;

describe('Infortunio e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let infortunioUpdatePage: InfortunioUpdatePage;
  let infortunioComponentsPage: InfortunioComponentsPage;
  /*let infortunioDeleteDialog: InfortunioDeleteDialog;*/

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Infortunios', async () => {
    await navBarPage.goToEntity('infortunio');
    infortunioComponentsPage = new InfortunioComponentsPage();
    await browser.wait(ec.visibilityOf(infortunioComponentsPage.title), 5000);
    expect(await infortunioComponentsPage.getTitle()).to.eq('nextrainingApp.infortunio.home.title');
  });

  it('should load create Infortunio page', async () => {
    await infortunioComponentsPage.clickOnCreateButton();
    infortunioUpdatePage = new InfortunioUpdatePage();
    expect(await infortunioUpdatePage.getPageTitle()).to.eq('nextrainingApp.infortunio.home.createOrEditLabel');
    await infortunioUpdatePage.cancel();
  });

  /* it('should create and save Infortunios', async () => {
        const nbButtonsBeforeCreate = await infortunioComponentsPage.countDeleteButtons();

        await infortunioComponentsPage.clickOnCreateButton();
        await promise.all([
            infortunioUpdatePage.setDataInizioInput('2000-12-31'),
            infortunioUpdatePage.setDataFineInput('2000-12-31'),
            infortunioUpdatePage.gravitaSelectLastOption(),
            infortunioUpdatePage.setDescrizioneInput('descrizione'),
            infortunioUpdatePage.specialistaSelectLastOption(),
            infortunioUpdatePage.calciatoreSelectLastOption(),
        ]);
        expect(await infortunioUpdatePage.getDataInizioInput()).to.eq('2000-12-31', 'Expected dataInizio value to be equals to 2000-12-31');
        expect(await infortunioUpdatePage.getDataFineInput()).to.eq('2000-12-31', 'Expected dataFine value to be equals to 2000-12-31');
        expect(await infortunioUpdatePage.getDescrizioneInput()).to.eq('descrizione', 'Expected Descrizione value to be equals to descrizione');
        await infortunioUpdatePage.save();
        expect(await infortunioUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await infortunioComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    });*/

  /* it('should delete last Infortunio', async () => {
        const nbButtonsBeforeDelete = await infortunioComponentsPage.countDeleteButtons();
        await infortunioComponentsPage.clickOnLastDeleteButton();

        infortunioDeleteDialog = new InfortunioDeleteDialog();
        expect(await infortunioDeleteDialog.getDialogTitle())
            .to.eq('nextrainingApp.infortunio.delete.question');
        await infortunioDeleteDialog.clickOnConfirmButton();

        expect(await infortunioComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });*/

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
