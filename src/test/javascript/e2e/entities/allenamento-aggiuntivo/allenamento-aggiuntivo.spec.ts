/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  AllenamentoAggiuntivoComponentsPage,
  AllenamentoAggiuntivoDeleteDialog,
  AllenamentoAggiuntivoUpdatePage
} from './allenamento-aggiuntivo.page-object';

const expect = chai.expect;

describe('AllenamentoAggiuntivo e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let allenamentoAggiuntivoUpdatePage: AllenamentoAggiuntivoUpdatePage;
  let allenamentoAggiuntivoComponentsPage: AllenamentoAggiuntivoComponentsPage;
  let allenamentoAggiuntivoDeleteDialog: AllenamentoAggiuntivoDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load AllenamentoAggiuntivos', async () => {
    await navBarPage.goToEntity('allenamento-aggiuntivo');
    allenamentoAggiuntivoComponentsPage = new AllenamentoAggiuntivoComponentsPage();
    await browser.wait(ec.visibilityOf(allenamentoAggiuntivoComponentsPage.title), 5000);
    expect(await allenamentoAggiuntivoComponentsPage.getTitle()).to.eq('nextrainingApp.allenamentoAggiuntivo.home.title');
  });

  it('should load create AllenamentoAggiuntivo page', async () => {
    await allenamentoAggiuntivoComponentsPage.clickOnCreateButton();
    allenamentoAggiuntivoUpdatePage = new AllenamentoAggiuntivoUpdatePage();
    expect(await allenamentoAggiuntivoUpdatePage.getPageTitle()).to.eq('nextrainingApp.allenamentoAggiuntivo.home.createOrEditLabel');
    await allenamentoAggiuntivoUpdatePage.cancel();
  });

  it('should create and save AllenamentoAggiuntivos', async () => {
    const nbButtonsBeforeCreate = await allenamentoAggiuntivoComponentsPage.countDeleteButtons();

    await allenamentoAggiuntivoComponentsPage.clickOnCreateButton();
    await promise.all([
      allenamentoAggiuntivoUpdatePage.setSportInput('sport'),
      allenamentoAggiuntivoUpdatePage.naturaSelectLastOption(),
      allenamentoAggiuntivoUpdatePage.setLavoroInput('lavoro'),
      allenamentoAggiuntivoUpdatePage.setDurataInput('5')
      // allenamentoAggiuntivoUpdatePage.calciatoreSelectLastOption(),
    ]);
    expect(await allenamentoAggiuntivoUpdatePage.getSportInput()).to.eq('sport', 'Expected Sport value to be equals to sport');
    expect(await allenamentoAggiuntivoUpdatePage.getLavoroInput()).to.eq('lavoro', 'Expected Lavoro value to be equals to lavoro');
    expect(await allenamentoAggiuntivoUpdatePage.getDurataInput()).to.eq('5', 'Expected durata value to be equals to 5');
    await allenamentoAggiuntivoUpdatePage.save();
    expect(await allenamentoAggiuntivoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await allenamentoAggiuntivoComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last AllenamentoAggiuntivo', async () => {
    const nbButtonsBeforeDelete = await allenamentoAggiuntivoComponentsPage.countDeleteButtons();
    await allenamentoAggiuntivoComponentsPage.clickOnLastDeleteButton();

    allenamentoAggiuntivoDeleteDialog = new AllenamentoAggiuntivoDeleteDialog();
    expect(await allenamentoAggiuntivoDeleteDialog.getDialogTitle()).to.eq('nextrainingApp.allenamentoAggiuntivo.delete.question');
    await allenamentoAggiuntivoDeleteDialog.clickOnConfirmButton();

    expect(await allenamentoAggiuntivoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
