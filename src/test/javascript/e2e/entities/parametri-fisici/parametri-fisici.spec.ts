/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ParametriFisiciComponentsPage, ParametriFisiciDeleteDialog, ParametriFisiciUpdatePage } from './parametri-fisici.page-object';

const expect = chai.expect;

describe('ParametriFisici e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let parametriFisiciUpdatePage: ParametriFisiciUpdatePage;
  let parametriFisiciComponentsPage: ParametriFisiciComponentsPage;
  /*let parametriFisiciDeleteDialog: ParametriFisiciDeleteDialog;*/

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ParametriFisicis', async () => {
    await navBarPage.goToEntity('parametri-fisici');
    parametriFisiciComponentsPage = new ParametriFisiciComponentsPage();
    await browser.wait(ec.visibilityOf(parametriFisiciComponentsPage.title), 5000);
    expect(await parametriFisiciComponentsPage.getTitle()).to.eq('nextrainingApp.parametriFisici.home.title');
  });

  it('should load create ParametriFisici page', async () => {
    await parametriFisiciComponentsPage.clickOnCreateButton();
    parametriFisiciUpdatePage = new ParametriFisiciUpdatePage();
    expect(await parametriFisiciUpdatePage.getPageTitle()).to.eq('nextrainingApp.parametriFisici.home.createOrEditLabel');
    await parametriFisiciUpdatePage.cancel();
  });

  /* it('should create and save ParametriFisicis', async () => {
        const nbButtonsBeforeCreate = await parametriFisiciComponentsPage.countDeleteButtons();

        await parametriFisiciComponentsPage.clickOnCreateButton();
        await promise.all([
            parametriFisiciUpdatePage.setDataRivelazioneInput('2000-12-31'),
            parametriFisiciUpdatePage.setMassaCorporeaInput('5'),
            parametriFisiciUpdatePage.setAltezzaInput('5'),
            parametriFisiciUpdatePage.setBmiInput('5'),
            parametriFisiciUpdatePage.condizioneSelectLastOption(),
            parametriFisiciUpdatePage.setFcRiposoInput('5'),
            parametriFisiciUpdatePage.calciatoreSelectLastOption(),
        ]);
        expect(await parametriFisiciUpdatePage.getDataRivelazioneInput()).to.eq('2000-12-31', 'Expected dataRivelazione value to be equals to 2000-12-31');
        expect(await parametriFisiciUpdatePage.getMassaCorporeaInput()).to.eq('5', 'Expected massaCorporea value to be equals to 5');
        expect(await parametriFisiciUpdatePage.getAltezzaInput()).to.eq('5', 'Expected altezza value to be equals to 5');
        expect(await parametriFisiciUpdatePage.getBmiInput()).to.eq('5', 'Expected bmi value to be equals to 5');
        expect(await parametriFisiciUpdatePage.getFcRiposoInput()).to.eq('5', 'Expected fcRiposo value to be equals to 5');
        await parametriFisiciUpdatePage.save();
        expect(await parametriFisiciUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await parametriFisiciComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    });*/

  /* it('should delete last ParametriFisici', async () => {
        const nbButtonsBeforeDelete = await parametriFisiciComponentsPage.countDeleteButtons();
        await parametriFisiciComponentsPage.clickOnLastDeleteButton();

        parametriFisiciDeleteDialog = new ParametriFisiciDeleteDialog();
        expect(await parametriFisiciDeleteDialog.getDialogTitle())
            .to.eq('nextrainingApp.parametriFisici.delete.question');
        await parametriFisiciDeleteDialog.clickOnConfirmButton();

        expect(await parametriFisiciComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });*/

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
