import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { InterviewdbSharedModule } from 'app/shared/shared.module';
import { IntervieweeComponent } from './interviewee.component';
import { IntervieweeDetailComponent } from './interviewee-detail.component';
import { IntervieweeUpdateComponent } from './interviewee-update.component';
import { IntervieweeDeleteDialogComponent } from './interviewee-delete-dialog.component';
import { intervieweeRoute } from './interviewee.route';

@NgModule({
  imports: [InterviewdbSharedModule, RouterModule.forChild(intervieweeRoute)],
  declarations: [IntervieweeComponent, IntervieweeDetailComponent, IntervieweeUpdateComponent, IntervieweeDeleteDialogComponent],
  entryComponents: [IntervieweeDeleteDialogComponent],
})
export class InterviewdbIntervieweeModule {}
