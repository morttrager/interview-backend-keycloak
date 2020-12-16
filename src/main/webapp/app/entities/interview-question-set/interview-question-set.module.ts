import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { InterviewdbSharedModule } from 'app/shared/shared.module';
import { InterviewQuestionSetComponent } from './interview-question-set.component';
import { InterviewQuestionSetDetailComponent } from './interview-question-set-detail.component';
import { InterviewQuestionSetUpdateComponent } from './interview-question-set-update.component';
import { InterviewQuestionSetDeleteDialogComponent } from './interview-question-set-delete-dialog.component';
import { interviewQuestionSetRoute } from './interview-question-set.route';

@NgModule({
  imports: [InterviewdbSharedModule, RouterModule.forChild(interviewQuestionSetRoute)],
  declarations: [
    InterviewQuestionSetComponent,
    InterviewQuestionSetDetailComponent,
    InterviewQuestionSetUpdateComponent,
    InterviewQuestionSetDeleteDialogComponent,
  ],
  entryComponents: [InterviewQuestionSetDeleteDialogComponent],
})
export class InterviewdbInterviewQuestionSetModule {}
