/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { AllenamentoComponentsPage, AllenamentoDeleteDialog, AllenamentoUpdatePage } from './allenamento.page-object';

const expect = chai.expect;

describe('Allenamento e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let allenamentoUpdatePage: AllenamentoUpdatePage;
  let allenamentoComponentsPage: AllenamentoComponentsPage;
  /*let allenamentoDeleteDialog: AllenamentoDeleteDialog;*/

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Allenamentos', async () => {
    await navBarPage.goToEntity('allenamento');
    allenamentoComponentsPage = new AllenamentoComponentsPage();
    await browser.wait(ec.visibilityOf(allenamentoComponentsPage.title), 5000);
    expect(await allenamentoComponentsPage.getTitle()).to.eq('nextrainingApp.allenamento.home.title');
  });

  it('should load create Allenamento page', async () => {
    await allenamentoComponentsPage.clickOnCreateButton();
    allenamentoUpdatePage = new AllenamentoUpdatePage();
    expect(await allenamentoUpdatePage.getPageTitle()).to.eq('nextrainingApp.allenamento.home.createOrEditLabel');
    await allenamentoUpdatePage.cancel();
  });

  /* it('should create and save Allenamentos', async () => {
        const nbButtonsBeforeCreate = await allenamentoComponentsPage.countDeleteButtons();

        await allenamentoComponentsPage.clickOnCreateButton();
        await promise.all([
            allenamentoUpdatePage.setDataSvolgimentoInput('2000-12-31'),
            allenamentoUpdatePage.naturaSelectLastOption(),
            allenamentoUpdatePage.setLavoroInput('lavoro'),
            // allenamentoUpdatePage.calciatoreSelectLastOption(),
        ]);
        expect(await allenamentoUpdatePage.getDataSvolgimentoInput()).to.eq('2000-12-31', 'Expected dataSvolgimento value to be equals to 2000-12-31');
        expect(await allenamentoUpdatePage.getLavoroInput()).to.eq('lavoro', 'Expected Lavoro value to be equals to lavoro');
        await allenamentoUpdatePage.save();
        expect(await allenamentoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await allenamentoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    });*/

  /* it('should delete last Allenamento', async () => {
        const nbButtonsBeforeDelete = await allenamentoComponentsPage.countDeleteButtons();
        await allenamentoComponentsPage.clickOnLastDeleteButton();

        allenamentoDeleteDialog = new AllenamentoDeleteDialog();
        expect(await allenamentoDeleteDialog.getDialogTitle())
            .to.eq('nextrainingApp.allenamento.delete.question');
        await allenamentoDeleteDialog.clickOnConfirmButton();

        expect(await allenamentoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });*/

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
