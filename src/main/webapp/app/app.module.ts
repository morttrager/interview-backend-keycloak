import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { InterviewdbSharedModule } from 'app/shared/shared.module';
import { InterviewdbCoreModule } from 'app/core/core.module';
import { InterviewdbAppRoutingModule } from './app-routing.module';
import { InterviewdbHomeModule } from './home/home.module';
import { InterviewdbEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    InterviewdbSharedModule,
    InterviewdbCoreModule,
    InterviewdbHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    InterviewdbEntityModule,
    InterviewdbAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent],
})
export class InterviewdbAppModule {}
