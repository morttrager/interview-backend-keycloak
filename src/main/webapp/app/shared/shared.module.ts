import { NgModule } from '@angular/core';
import { InterviewdbSharedLibsModule } from './shared-libs.module';
import { AlertComponent } from './alert/alert.component';
import { AlertErrorComponent } from './alert/alert-error.component';
import { HasAnyAuthorityDirective } from './auth/has-any-authority.directive';

@NgModule({
  imports: [InterviewdbSharedLibsModule],
  declarations: [AlertComponent, AlertErrorComponent, HasAnyAuthorityDirective],
  exports: [InterviewdbSharedLibsModule, AlertComponent, AlertErrorComponent, HasAnyAuthorityDirective],
})
export class InterviewdbSharedModule {}
